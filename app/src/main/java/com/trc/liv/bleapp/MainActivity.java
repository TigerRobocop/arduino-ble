package com.trc.liv.bleapp;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnConnect;
    Button btnSend;
    ToggleButton btnToggle;
    SeekBar seekBar;
    TextView txtLedValue;
    Camera camera;

    boolean lightsOn = false;

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
        btnToggle = findViewById(R.id.btn_toggle);
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

        btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                try {
                    if (checked) {
                        flashlightOn();

                    } else {
                        flashlightOff();
                    }
                } catch (Exception e) {
                    Log.d("BLE", e.getMessage());
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

    public void flashlightOn() {
        CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (!lightsOn) {
                String cameraId = cm.getCameraIdList()[0];
                cm.setTorchMode(cameraId, true);
                lightsOn = true;
            }
        } catch (CameraAccessException e) {

        }

    }

    public void flashlightOff() {
        CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (lightsOn) {
                String cameraId = cm.getCameraIdList()[0];
                cm.setTorchMode(cameraId, false);
                lightsOn = false;
            }
        } catch (CameraAccessException e) {

        }
    }
}


// 1. inicializar bluetooth - bluetooth controller

// 2. listar dispositivos

// 3. conectando - criar conexão

// 4. enviar informações

// 5. ler informações do bluetooth