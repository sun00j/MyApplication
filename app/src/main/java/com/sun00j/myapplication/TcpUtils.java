package com.sun00j.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by lewa on 10/26/15.
 */
public class TcpUtils implements Runnable {
    private final String TAG = "TcpUtils";
    private Socket mSocket;
    private BufferedReader br;
    private OutputStream os;
    private String url = "10.0.4.170";
    public void TcpUtils() {
        try {
            mSocket = new Socket(url,6543);
            //br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            //os = mSocket.getOutputStream();
            //os.write("index#2#id#2".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        String content = null;
        try {
            mSocket = new Socket(url,6543);
            br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            os = mSocket.getOutputStream();
            os.write("index#1#id#1\n".getBytes());
            os.flush();
            while((content = br.readLine()) != null) {
                Log.d(TAG,"receive -->"+content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
