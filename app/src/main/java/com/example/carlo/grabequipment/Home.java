package com.example.carlo.grabequipment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Objects.UserContract;
import Objects.UserDatabaseHelper;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private DatabaseReference mDatabase;

    private String fname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView hello = (TextView)findViewById(R.id.textView_hello);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(Home.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                   mDatabase = mDatabase.child(user_id);

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            fname = dataSnapshot.child("firstName").getValue(String.class);
                            hello.setText("Hello, "+fname);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };

    }
    public void get(View view)
    {
        Intent intent = new Intent(this, ViewEquipment.class);
        intent.putExtra("isAdmin", false);

        startActivity(intent);
    }
    public void request(View view)
    {
        Intent intent = new Intent(this, Request.class);

        intent.putExtra("isAdmin", false);

        startActivity(intent);
    }
    public void change(View view)
    {
        Intent intent = new Intent(this, ChangePassword.class);
        //intent.putExtra("id", id);
        startActivity(intent);
    }
    public void logout(View view)
    {
        mAuth.signOut();

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }
}
