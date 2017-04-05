package com.example.carlo.grabequipment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GetEquipment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_equipment);
    }
    public void get(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
