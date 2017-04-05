package com.example.carlo.grabequipment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(AdminHome.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };
    }

    public void view(View view)
    {
        Intent intent = new Intent(this, ViewEquipment.class);

        intent.putExtra("isAdmin", true);

        startActivity(intent);
    }

    public void request(View view)
    {
        Intent intent = new Intent(this, AdminRequest.class);

        intent.putExtra("isAdmin", true);

        startActivity(intent);
    }

    public void history(View view)
    {
        Intent intent = new Intent(this, AdminHistory.class);
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
