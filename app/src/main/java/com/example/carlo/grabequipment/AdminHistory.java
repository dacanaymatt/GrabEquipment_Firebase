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

import Helper.EquipmentAdapter;
import Helper.HistoryAdapter;
import Model.BorrowDetail;
import Model.Equipment;

public class AdminHistory extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView lvHistory;
    private HistoryAdapter adapter;
    private List<BorrowDetail> mBorrowDetail;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(AdminHistory.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        mProgress = new ProgressDialog(this);

        lvHistory = (ListView) findViewById(R.id.listView_request);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mBorrowDetail = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot requestSnapshot: dataSnapshot.child("Requests").getChildren()) {

                    mProgress.setMessage("Loading ...");
                    mProgress.show();

                    BorrowDetail borrowDetail = new BorrowDetail();

                    borrowDetail.setStudentNumber(requestSnapshot.child("studentNumber").getValue().toString().substring(0, 10));
                    borrowDetail.setStatus(requestSnapshot.child("status").getValue().toString());
                    borrowDetail.setDateBorrowed(requestSnapshot.child("dateBorrowed").getValue().toString());
                    borrowDetail.setDateReturned(requestSnapshot.child("dateReturned").getValue().toString());
                    borrowDetail.setEquipment(requestSnapshot.child("deviceBorrowed").getValue().toString());
                    mBorrowDetail.add(borrowDetail);
                }

                adapter = new HistoryAdapter(getApplicationContext(), mBorrowDetail);

                lvHistory.setAdapter(adapter);

                mProgress.dismiss();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
