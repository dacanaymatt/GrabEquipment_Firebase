package com.example.carlo.grabequipment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Helper.EquipmentAdapter;
import Model.Equipment;

public class ViewEquipment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView lvEquipment;
    private EquipmentAdapter adapter;
    private List<Equipment> mEquipmentList;
    private DatabaseReference mDatabase;

    private boolean isAdmin;

    /*@Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ViewEquipment.this, AdminHome.class);

        startActivity(intent);

        super.onBackPressed();  // optional depending on your needs
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_equipment);

        Bundle bundle= getIntent().getExtras();

        isAdmin = bundle.getBoolean("isAdmin");

        if(!isAdmin) {
            Button addButton = (Button)findViewById(R.id.button_add);
            addButton.setVisibility(View.GONE);
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(ViewEquipment.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        lvEquipment = (ListView) findViewById(R.id.listView_equipments);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mEquipmentList = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot equipmentSnapshot : dataSnapshot.child("Equipment").getChildren()) {

                    Equipment equipment = new Equipment();

                    equipment.setName(String.valueOf(equipmentSnapshot.child("name").getValue()));
                    equipment.setAmount(Integer.parseInt(String.valueOf(equipmentSnapshot.child("amount").getValue())));

                    mEquipmentList.add(equipment);

                }

                adapter = new EquipmentAdapter(getApplicationContext(), mEquipmentList);

                lvEquipment.setAdapter(adapter);

                lvEquipment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView equipmentTypeText = (TextView)(view.findViewById(R.id.textView_equipment_type));
                        TextView equipmentAmountText = (TextView)(view.findViewById(R.id.textView_equipment_amount));

                        String equipmentType = equipmentTypeText.getText().toString();
                        String equipmentAmount = equipmentAmountText.getText().toString();

                        if(isAdmin) {

                            Intent intent = new Intent(ViewEquipment.this, EditEquipment.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("type", equipmentType);
                            bundle.putString("amount", equipmentAmount);

                            intent.putExtras(bundle);

                            startActivity(intent);
                        }

                        else {

                            for (DataSnapshot equipmentSnapshot : dataSnapshot.child("Equipment").getChildren()) {

                                if(equipmentSnapshot.child("name").getValue() == equipmentType) {

                                    int amountLeft = Integer.parseInt(equipmentSnapshot.child("amount").getValue().toString());
                                    amountLeft--;

                                    equipmentSnapshot.child("amount").getRef().setValue(amountLeft);

                                    String uid = mAuth.getCurrentUser().getUid();
                                    String email = mAuth.getCurrentUser().getEmail();

                                    String firstName = dataSnapshot.child("Users").child(uid).child("firstName").getValue().toString();
                                    String lastName = dataSnapshot.child("Users").child(uid).child("lastName").getValue().toString();

                                    Date date = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
                                    String formattedDate = sdf.format(date);

                                    String key = mDatabase.child("Requests").push().getKey();
                                    mDatabase.child("Requests").child(key).child("lastName").setValue(lastName);
                                    mDatabase.child("Requests").child(key).child("firstName").setValue(firstName);
                                    mDatabase.child("Requests").child(key).child("timeBorrowed").setValue(formattedDate);
                                    mDatabase.child("Requests").child(key).child("studentNumber").setValue(email);
                                    mDatabase.child("Requests").child(key).child("status").setValue("Borrowed");
                                    mDatabase.child("Requests").child(key).child("userID").setValue(uid);

                                    Toast.makeText(ViewEquipment.this, "Successfully requested for a/an '"
                                            + equipmentType + ",. Please go to the technician as soon as possible.",
                                            Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(ViewEquipment.this, Home.class);
                                    startActivity(intent);

                                    break;
                                }

                            }
                        }

                    }

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void buttonClick(View view)
    {
        Intent intent = new Intent(this, AddEquipment.class);
        startActivity(intent);
    }

}
