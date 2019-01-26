package com.trc.liv.bleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnConnect;
    Button btnSend;

    static BluetoothController myController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (myController == null) {
            myController = new BluetoothController(this);
        }

        btnConnect = findViewById(R.id.btn_connect);
        btnSend = findViewById(R.id.btn_send);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> pairedDevices = myController.getPairedDevices();

                for (String deviceInfo : pairedDevices) {
                    if (deviceInfo.contains("livble")) {
                        String[] splitInfo = deviceInfo.split("_");
                        String address = splitInfo[1];

                        myController.connect(address);

                        Log.d("BLE", address);
                    }
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}


// 1. inicializar bluetooth - bluetooth controller

// 2. listar dispositivos

// 3. conectando - criar conexão