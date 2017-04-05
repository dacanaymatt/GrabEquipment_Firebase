package com.example.carlo.grabequipment;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Carlo on 03/27/2017.
 */

public class GrabEquipment_Firebase extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
