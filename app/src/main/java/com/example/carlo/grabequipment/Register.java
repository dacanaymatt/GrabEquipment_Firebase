package com.example.carlo.grabequipment;

import android.app.ProgressDialog;
import android.content.ContentValues;
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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Objects.UserContract;
import Objects.UserDatabaseHelper;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);
    }

    public void register(View view)
    {
        EditText fnameView = (EditText)findViewById(R.id.editText_fname);
        EditText lnameView = (EditText)findViewById(R.id.editText_lname);
        EditText sidView = (EditText)findViewById(R.id.editText_id);
        EditText passView = (EditText)findViewById(R.id.editText_password);
        EditText cpassView = (EditText)findViewById(R.id.editText_cpassword);

        final String fname = fnameView.getText().toString().trim();
        final String lname = lnameView.getText().toString().trim();
        String sid = sidView.getText().toString().trim()+"@gmail.com";
        final String pass = passView.getText().toString().trim();
        String cpass = cpassView.getText().toString().trim();

        if(!TextUtils.isEmpty(sid)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(fname)){

            if(pass.equals(cpass)){
                mProgress.setMessage("Signing Up ...");
                mProgress.show();

                mAuth.createUserWithEmailAndPassword(sid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user_id = mAuth.getCurrentUser().getUid();

                            DatabaseReference current_user_id = mDatabase.child(user_id);
                            current_user_id.child("firstName").setValue(fname);
                            current_user_id.child("lastName").setValue(lname);
                            current_user_id.child("password").setValue(pass);

                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_LONG).show();

                            mProgress.dismiss();

                            Intent intent = new Intent(Register.this,Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else{
                            mProgress.dismiss();
                            Toast.makeText(Register.this,"User creation failed",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }else{
                Toast.makeText(Register.this,"Password do not match",Toast.LENGTH_LONG).show();
            }
        }



    }
}
