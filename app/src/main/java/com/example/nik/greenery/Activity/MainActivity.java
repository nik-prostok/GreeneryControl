package com.example.nik.greenery.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nik.greenery.BluetoothConnection.BluetoothConnection;
import com.example.nik.greenery.Fragments.SensorsFragment;
import com.example.nik.greenery.Fragments.BluetoothFragment;
import com.example.nik.greenery.Fragments.SetupFragment;
import com.example.nik.greenery.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BluetoothFragment.OnFragmentInteractionListener {

    NavigationView navigationView;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    String nameDevice;
    BluetoothConnection bluetoothConnection;

    BluetoothFragment bluetoothFragment;
    SensorsFragment sensorsFragment;
    SetupFragment setupFragment;

    boolean syncFlag = true;
    boolean manualFlag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.bluetooth_settings);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.bluetooth_settings));


        //Bluetooth setup
        if (bluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.enableDebugLogging(true);

        bluetoothFragment = BluetoothFragment.newInstance();
        fragmentTransaction
                .add(R.id.container, bluetoothFragment)
                .commit();

        fragmentManager.executePendingTransactions();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_sync_time:
                syncFlag = !syncFlag;
                item.setChecked(syncFlag);
                if (setupFragment != null)
                    setupFragment.setSyncTime(syncFlag);
                return true;
            case R.id.action_manual_mode:
                manualFlag = !manualFlag;
                item.setChecked(manualFlag);
                if (sensorsFragment != null){
                    Handler mHandler = sensorsFragment.getHandlerManualMode();
                    Message writtenMsg = mHandler.obtainMessage(1, manualFlag);
                    mHandler.sendMessage(writtenMsg);
                    sensorsFragment.setManualMode(manualFlag);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        String TAG;
        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.enableDebugLogging(true);


        switch (id) {
            case R.id.bluetooth_settings:
                TAG = "bluetooth_fragment";
                if (bluetoothConnection != null){
                    bluetoothConnection.closeSocket();
                }
                bluetoothFragment = BluetoothFragment.newInstance();
                fragmentTransaction
                        .replace(R.id.container, bluetoothFragment, TAG)
                        .commit();
                break;
            case R.id.nav_sensor:
                TAG = "sensors_fragment";
                if (bluetoothFragment != null) {
                    nameDevice = bluetoothFragment.getName();
                    bluetoothConnection = bluetoothFragment.getBluetoothConnection();
                  //  Log.d("mainActivityBluetooth", String.valueOf(bluetoothConnection.isConnected()));
                    if (nameDevice == null) {
                        Snackbar.make(navigationView, "Нет подключения, выберите устройство", Snackbar.LENGTH_LONG).show();
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(this);

                        MenuItem menuItem = (MenuItem) navigationView.getMenu().findItem(R.id.bluetooth_settings);
                        menuItem.setChecked(true);

                        onNavigationItemSelected(menuItem);
                        return false;
                    }
                    sensorsFragment = SensorsFragment.newInstance(nameDevice);
                    sensorsFragment.setBluetoothConnection(bluetoothConnection);
                    fragmentTransaction
                            .replace(R.id.container, sensorsFragment, TAG)
                            .commit();
                } else {
                    Snackbar.make(navigationView, "Нет подключения, выберите устройство", Snackbar.LENGTH_LONG).show();

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);

                    MenuItem menuItem = (MenuItem) navigationView.getMenu().findItem(R.id.bluetooth_settings);
                    menuItem.setChecked(true);

                    onNavigationItemSelected(menuItem);

                    return false;
                }
                break;
            case R.id.nav_manage:
                TAG = "setup_fragment";
                if (bluetoothFragment != null) {
                    nameDevice = bluetoothFragment.getName();
                    bluetoothConnection = bluetoothFragment.getBluetoothConnection();
                    if (nameDevice == null) {
                        Snackbar.make(navigationView, "Нет подключения, выберите устройство", Snackbar.LENGTH_LONG).show();
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(this);

                        MenuItem menuItem = (MenuItem) navigationView.getMenu().findItem(R.id.bluetooth_settings);
                        menuItem.setChecked(true);

                        onNavigationItemSelected(menuItem);
                        return false;
                    }

                    setupFragment = SetupFragment.newInstance(nameDevice);
                    setupFragment.setBluetoothConnection(bluetoothConnection);
                    setupFragment.setSyncTime(syncFlag);
                    fragmentTransaction
                            .replace(R.id.container, setupFragment, TAG)
                            .commit();
                } else {
                    Snackbar.make(navigationView, "Нет подключения, выберите устройство", Snackbar.LENGTH_LONG).show();

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);

                    MenuItem menuItem = (MenuItem) navigationView.getMenu().findItem(R.id.bluetooth_settings);
                    menuItem.setChecked(true);

                    onNavigationItemSelected(menuItem);

                    return false;
                }
            default:
                TAG = null;
        }

        fragmentManager.executePendingTransactions();

        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        // Выводим выбранный пункт в заголовке
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String link) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment sensorsFragment = fragmentManager.findFragmentByTag("sensors_fragment");
        if (sensorsFragment == null) {
            Log.d("sensorsFragmentStatus", "NULL");
        } else {
            Log.d("sensorsFragmentStatus", "OK!");
        }

    }
}
