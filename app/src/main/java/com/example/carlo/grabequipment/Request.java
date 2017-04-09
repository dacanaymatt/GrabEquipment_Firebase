package com.example.carlo.grabequipment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Helper.RequestAdapter;
import Helper.StudentRequestAdapter;
import Model.StudentRequestDetail;
import Model.StudentRequestDetail;

public class Request extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private ListView lvStudentRequest;
    private StudentRequestAdapter adapter;
    private List<StudentRequestDetail> mStudentRequestList;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(Request.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        mProgress = new ProgressDialog(this);

        lvStudentRequest = (ListView) findViewById(R.id.listView_request);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStudentRequestList = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mProgress.setTitle("Loading...");
                mProgress.show();

                for(DataSnapshot requestSnapshot : dataSnapshot.child("Requests").getChildren()) {

                    if(requestSnapshot.child("userID").getValue().toString().equals(mAuth.getCurrentUser().getUid().toString())) {

                        StudentRequestDetail request = new StudentRequestDetail();

                        request.setStatus(requestSnapshot.child("status").getValue().toString());
                        request.setDateBorrowed(requestSnapshot.child("dateBorrowed").getValue().toString());
                        request.setTimeBorrowed(requestSnapshot.child("timeBorrowed").getValue().toString());
                        request.setDateReturned(requestSnapshot.child("dateReturned").getValue().toString());
                        request.setEquipment(requestSnapshot.child("deviceBorrowed").getValue().toString());

                        mStudentRequestList.add(request);
                    }
                }

                adapter = new StudentRequestAdapter(getApplicationContext(), mStudentRequestList);

                lvStudentRequest.setAdapter(adapter);

                mProgress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
