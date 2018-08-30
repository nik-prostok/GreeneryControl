package com.example.nik.greenery.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.nik.greenery.Activity.MainActivity;

/**
 * Created by Nik on 12.06.2018.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
