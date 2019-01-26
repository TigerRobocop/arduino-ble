package com.trc.liv.bleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnConnect;
    Button btnSend;
    SeekBar seekBar;
    TextView txtLedValue;

    static BluetoothController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (controller == null) {
            controller = new BluetoothController(this);
        }

        btnConnect = findViewById(R.id.btn_connect);
        btnSend = findViewById(R.id.btn_send);
        seekBar = findViewById(R.id.seekbar_led);
        txtLedValue = findViewById(R.id.txt_ledValue);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> pairedDevices = controller.getPairedDevices();

                for (String deviceInfo : pairedDevices) {
                    if (deviceInfo.contains("livble")) {
                        String[] splitInfo = deviceInfo.split("_");
                        String address = splitInfo[1];

                        controller.connect(address);

                        Log.d("BLE", address);
                    }
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.send("uma string maior");

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                txtLedValue.setText(progress + "");
                controller.setLed(progress);
            }
        });
    }
}


// 1. inicializar bluetooth - bluetooth controller

// 2. listar dispositivos

// 3. conectando - criar conexão

// 4. enviar informações

// 5. ler informações do bluetooth