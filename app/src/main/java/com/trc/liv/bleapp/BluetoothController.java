package com.trc.liv.bleapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothController {

    Activity parent;
    BluetoothAdapter myAdapter;
    BluetoothSocket mySocket;





    BluetoothController (Activity parent) {
        this.parent = parent;
        myAdapter = BluetoothAdapter.getDefaultAdapter();

        if (myAdapter == null) {
            Toast.makeText(this.parent.getApplicationContext(),
                    "no bluetooth available",
                    Toast.LENGTH_LONG).show();
        } else if (!myAdapter.isEnabled()) {
            Intent bluetoothPermission = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.parent.startActivityForResult(bluetoothPermission, 1);
        }
    }

    // getters
    public ArrayList<String> getPairedDevices() {
        ArrayList<String> pairedDevices = new ArrayList<String>();

        Set pairedObject = this.myAdapter.getBondedDevices();

        for (Object object : pairedObject) {
            BluetoothDevice bd = (BluetoothDevice) object;

            String newInfo = bd.getName() + "_" + bd.getAddress();

            pairedDevices.add(newInfo);

            Log.d("BLE", newInfo);
        }

        return pairedDevices;

    }

    public Activity getParent(){
        return this.parent;
    }

    public BluetoothAdapter getAdapter(){
        return this.myAdapter;
    }

    public BluetoothSocket getSocket() {
        return this.mySocket;
    }

    // setters
    public void setSocket(BluetoothSocket socket) {
        this.mySocket = socket;
    }
}
