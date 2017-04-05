package com.example.carlo.grabequipment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Objects.UserContract;
import Objects.UserDatabaseHelper;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    redirect(firebaseAuth);

                }
            }
        };
        mProgress = new ProgressDialog(this);
//
    }
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void redirect(FirebaseAuth firebaseAuth){
        String user_id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference current_user_id = mDatabase.child(user_id+"/admin");

        current_user_id.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Intent intent = new Intent(Login.this, AdminHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void login(View view)
    {

        EditText idView = (EditText)findViewById(R.id.editText_id);
        EditText passView = (EditText)findViewById(R.id.editText_password);

        String id = idView.getText().toString().trim()+"@gmail.com";
        String pass = passView.getText().toString().trim();

        if(!TextUtils.isEmpty(id)&&!TextUtils.isEmpty(pass)){
            mProgress.setMessage("Logging in ...");
            mProgress.show();
            mAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        mProgress.dismiss();
                        redirect(mAuth);
                    }else{
                        mProgress.dismiss();
                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void register(View view)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void forgot(View view)
    {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
}
