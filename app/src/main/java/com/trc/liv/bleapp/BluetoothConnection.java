package com.trc.liv.bleapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.UUID;

public class BluetoothConnection extends AsyncTask<Void, Void, Void> {

    BluetoothController controller;
    String addressToConnect;
    boolean connectionSuccess;


    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothConnection(BluetoothController controller, String address) {
        this.controller = controller;
        this.addressToConnect = address;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Toast.makeText(this.controller.getParent().getApplicationContext(),
                "Connecting to bluetooth",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (this.connectionSuccess) {
            Toast.makeText(this.controller.getParent().getApplicationContext(),
                    "Connected to bluetooth",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.controller.getParent().getApplicationContext(),
                    "Connection to bluetooth failed",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            if (controller.getSocket() == null) {
                BluetoothDevice device = this.controller.getAdapter().getRemoteDevice(this.addressToConnect);
                BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                socket.connect();

                this.controller.setSocket(socket);
            }

            this.connectionSuccess = true;

        } catch (Exception e) {
this.connectionSuccess= false;
        }
        return null;
    }
}
