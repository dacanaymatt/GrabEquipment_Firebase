package com.example.carlo.grabequipment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Helper.EquipmentAdapter;
import Helper.RequestAdapter;
import Model.Equipment;
import Model.RequestDetail;

public class AdminRequest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private ListView lvRequest;
    private RequestAdapter adapter;
    private List<RequestDetail> mRequestList;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(AdminRequest.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        mProgress = new ProgressDialog(this);

        lvRequest = (ListView) findViewById(R.id.listView_request);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRequestList = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mProgress.setTitle("Loading...");
                mProgress.show();
                for(DataSnapshot requestSnapshot : dataSnapshot.child("Requests").getChildren()) {

                    if(requestSnapshot.child("status").getValue().toString().equals("Borrowed")) {

                        RequestDetail request = new RequestDetail();

                        request.setFirstName(requestSnapshot.child("firstName").getValue().toString());
                        request.setLastName(requestSnapshot.child("lastName").getValue().toString());
                        request.setStudentNumber(requestSnapshot.child("studentNumber").getValue().toString().substring(0, 10));
                        request.setStatus(requestSnapshot.child("status").getValue().toString());
                        request.setDateBorrowed(requestSnapshot.child("dateBorrowed").getValue().toString());
                        request.setEquipment(requestSnapshot.child("deviceBorrowed").getValue().toString());

                        mRequestList.add(request);
                    }
                }

                adapter = new RequestAdapter(getApplicationContext(), mRequestList);

                lvRequest.setAdapter(adapter);

                mProgress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
