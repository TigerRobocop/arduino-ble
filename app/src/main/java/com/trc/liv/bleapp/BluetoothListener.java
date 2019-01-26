package com.trc.liv.bleapp;

import android.widget.Toast;

import java.io.InputStream;

public class BluetoothListener extends Thread {

    BluetoothController controller;
    boolean isRunning = false;
    InputStream bluetoothIn;

    BluetoothListener(BluetoothController controller) {
        this.controller = controller;

        try {
            this.bluetoothIn = this.controller.getSocket().getInputStream();

            this.isRunning = true;
        } catch (Exception e) {
            Toast.makeText(this.controller.getParent().getApplicationContext(),
                    "Reading from socket failed",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void run() {
        byte[] buffer = new byte[256];
        int bytesRead = 0;
        int latestPos = 0;

        while (this.isRunning) {
            try {
                int bytesAvailable = this.bluetoothIn.available();

                if (bytesAvailable > 0) {
                    bytesRead = this.bluetoothIn.read(buffer, latestPos, bytesAvailable);
                    latestPos += bytesRead;

                    String readText = new String(buffer, 0, latestPos);

                    if (readText.contains("\n")) {
                        latestPos = 0;
                        this.controller.getParent().runOnUiThread(new actionToDo(this.controller, readText));
                    }
                }
            } catch (Exception e) {

            }
        }
    }


    public void stopThread() {
        this.isRunning = false;
    }

    class actionToDo implements Runnable {
        BluetoothController controller;
        String received;

        actionToDo(BluetoothController controller, String received) {
            this.controller = controller;
            this.received = received;
        }


        @Override
        public void run() {

            Toast.makeText(this.controller.getParent().getApplicationContext(),
                    "Received: " + this.received,
                    Toast.LENGTH_LONG).show();

        }
    }
}
