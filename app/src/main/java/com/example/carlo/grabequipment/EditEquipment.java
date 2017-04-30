package com.example.carlo.grabequipment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Helper.EquipmentAdapter;
import Model.Equipment;

public class EditEquipment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_equipment);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(EditEquipment.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Equipment");

        EditText equipmentTypeEditText = (EditText)findViewById(R.id.editText_new_equipment_name);
        EditText equipmentAmountEditText = (EditText)findViewById(R.id.editText_new_equipment_amount);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String equipmentTypeText = bundle.getString("type");
        String equipmentAmountText = bundle.getString("amount");

        equipmentTypeEditText.setText(equipmentTypeText);
        equipmentTypeEditText.setEnabled(false);
        equipmentAmountEditText.setText(equipmentAmountText);

    }

    public void edit(View view) {

        EditText equipmentTypeField =  (EditText)findViewById(R.id.editText_new_equipment_name);
        EditText equipmentAmountField =  (EditText)findViewById(R.id.editText_new_equipment_amount);

        final String equipmentType = equipmentTypeField.getText().toString().toUpperCase();
        final String equipmentAmount = equipmentAmountField.getText().toString();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot equipmentSnapshot : dataSnapshot.getChildren()) {

                    if(equipmentSnapshot.child("name").getValue().toString().equals(equipmentType)) {

                        equipmentSnapshot.child("amount").getRef().setValue(equipmentAmount);

                        Toast.makeText(EditEquipment.this,"Equipment type '" + equipmentType +
                                "' updated.",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditEquipment.this, ViewEquipment.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("isAdmin", true);
                        startActivity(intent);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
