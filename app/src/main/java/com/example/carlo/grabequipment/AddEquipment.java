package com.example.carlo.grabequipment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEquipment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Equipment");
        mProgress = new ProgressDialog(this);
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(AddEquipment.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };
    }

    public void add(View view)
    {
        EditText equipmentTypeField =  (EditText)findViewById(R.id.editText_etype);
        EditText equipmentAmountField =  (EditText)findViewById(R.id.editText_eamount);

        final String equipmentType = equipmentTypeField.getText().toString().toUpperCase();
        final String equipmentAmount = equipmentAmountField.getText().toString();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean isExisting = false;
                for(DataSnapshot equipmentSnapshot : dataSnapshot.getChildren()) {

                    Log.d("DEBUG: ", equipmentSnapshot.toString());

                    if(equipmentSnapshot.child("name").getValue().toString().equals(equipmentType)) {
                        Toast.makeText(AddEquipment.this,"Equipment type '" + equipmentType +
                                "' already exists.",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AddEquipment.this, ViewEquipment.class);
                        intent.putExtra("isAdmin", true);
                        startActivity(intent);

                        isExisting = true;
                        break;
                    }

                    else {
                        continue;
                    }
                }
                if(!isExisting) {
                    mProgress.setMessage("Adding Equipment");
                    mProgress.show();

                    String key = mDatabase.push().getKey();

                    mDatabase.child(key).setValue(key);
                    mDatabase.child(key + "/name").setValue(equipmentType);
                    mDatabase.child(key + "/amount").setValue(equipmentAmount);

                    Toast.makeText(AddEquipment.this,"Equipment type '" + equipmentType + "' with an amount of "
                            + equipmentAmount + " added",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AddEquipment.this, ViewEquipment.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("isAdmin", true);
                    startActivity(intent);

                    mProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
