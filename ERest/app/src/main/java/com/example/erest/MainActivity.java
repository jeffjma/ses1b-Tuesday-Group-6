package com.example.erest;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.Map;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private Button mBtnReg;
    private TextView mTvTitle;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private FirebaseAuth firebaseAuth;
    private String str_username;
    private String str_password;
    private DatabaseReference myRef;
    private String target_user;
    private String target_usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);
        //button 声明
        //1. button order
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnReg = findViewById(R.id.btn_reg);
        mTvTitle = findViewById(R.id.tv_header);
        mEtEmail = findViewById(R.id.et_email);
        mEtPassword = findViewById(R.id.et_password);

        //get firebase information
        firebaseAuth = firebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //get data from realtime database
        myRef = FirebaseDatabase.getInstance().getReference();

        //Login Button when clicked run validation method
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_username = mEtEmail.getText().toString();
                str_password = mEtPassword.getText().toString();

                if ( str_username.length()<3 || !str_username.contains("@"))
                {
                    Toast.makeText(MainActivity.this,"Invalid email entered!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    getUserType();
                    validate(str_username, str_password);
                    ((eRestaurantApplication) getApplication()).setUserEmail(str_username);
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Registration Button when clicked go to registration page
        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
    //get data from database
    private void getUserType()
    {
        final Query query = myRef.child("Users");
        query.addValueEventListener(new ValueEventListener() {
            String userEmail = str_username.replace(".", "_dot_");

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                if(snapshot.hasChild(userEmail)) {
                    target_usertype = snapshot.child(userEmail).child("usertype").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //validation method
    private void validate (String userEmail, String userPassword) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(target_usertype.equals("staff"))
                    {
                        startActivity(new Intent(MainActivity.this,AdminMenu.class));
                        finish();
                    }
                    else if(target_usertype.equals("customer"))
                    {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Username or Password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}