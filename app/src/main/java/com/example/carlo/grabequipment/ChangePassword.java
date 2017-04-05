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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Objects.UserContract;
import Objects.UserDatabaseHelper;

public class ChangePassword extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String password;
    private FirebaseUser user;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users/"+user.getUid());
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                password = dataSnapshot.child("password").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mProgress = new ProgressDialog(this);
    }
    public void change(View view)
    {
        EditText passView = (EditText)findViewById(R.id.editText_password);
        EditText npassView = (EditText)findViewById(R.id.editText_npassword);
        EditText cpassView = (EditText)findViewById(R.id.editText_cpassword);

        String pass = passView.getText().toString();
        final String npass = npassView.getText().toString();
        String cpass = cpassView.getText().toString();

        if(!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(npass)&&!TextUtils.isEmpty(cpass)){
            if(npass.equals(cpass)){
                if(password.equals(pass)){
                    mProgress.setMessage("Loading ...");
                    mProgress.show();
                    user.updatePassword(npass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mDatabase.child("password").setValue(npass);
                                mProgress.dismiss();
                                Toast.makeText(ChangePassword.this,"Password change successful",Toast.LENGTH_LONG).show();
                            }else{
                                mProgress.dismiss();
                                Toast.makeText(ChangePassword.this,"Password change failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(ChangePassword.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(ChangePassword.this,"Passwords do not match",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(ChangePassword.this,"Fill up all fields",Toast.LENGTH_LONG).show();
        }



    }
}
