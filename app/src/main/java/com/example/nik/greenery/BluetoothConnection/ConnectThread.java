package com.example.nik.greenery.BluetoothConnection;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Nik on 25.05.2018.
 */

public class ConnectThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    private Handler mHandler;

    public ConnectThread(BluetoothSocket socket, Handler mHandler){
        mmSocket= socket;
        this.mHandler = mHandler;
        InputStream tmpIn=null;
        OutputStream tmpOut=null;

        // Получить входящий и исходящий потоки данных
        try{
            tmpIn= socket.getInputStream();
            tmpOut= socket.getOutputStream();
        } catch(IOException e){}

        mmInStream= tmpIn;
        mmOutStream= tmpOut;
    }

    public void run(){
        byte[] buffer=new byte[1024];// буферный массив
        int bytes;// bytes returned from read()

        // Прослушиваем InputStream пока не произойдет исключение
        while(true){
            try{
            // читаем из InputStream
                bytes= mmInStream.read(buffer);
                Log.d("InStream", String.valueOf(bytes));
                // посылаем прочитанные байты главной деятельности
                mHandler.obtainMessage(1, bytes,-1, buffer)
                        .sendToTarget();
            } catch(IOException e){
                break;
            }
        }
    }

    /* Вызываем этот метод из главной деятельности, чтобы отправить данные
    удаленному устройству */
    public void write(byte[] bytes){
        try{
            mmOutStream.write(bytes);
        } catch(IOException e){}
    }

    /* Вызываем этот метод из главной деятельности,
    чтобы разорвать соединение */
    public void cancel(){
        try{
            mmSocket.close();
        } catch(IOException e){}
    }

}
