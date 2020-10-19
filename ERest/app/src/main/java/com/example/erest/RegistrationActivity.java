package com.example.erest;


import android.content.Intent;
import android.util.Log;
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


public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private EditText firstName;
    private EditText lastName;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = firebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    //Upload account details to database
                    final String userEmail = email.getText().toString().trim();
                    String userPass = pass.getText().toString().trim();
                    final String fName = firstName.getText().toString().trim();
                    final String lName = lastName.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser signed = task.getResult().getUser();
                                signed.sendEmailVerification();
                                registerNewUser(userEmail.replace(".", "_dot_"), fName, lName,"customer");
                                Toast.makeText(RegistrationActivity.this, "You have successfully Registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegistrationActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void sendRegistrationEmail() {

    }

    private void registerNewUser(String email, String firstName, String lastName, String usertype) {
        User newUser = new User(firstName, lastName, usertype);
        mDatabase.child("Users").child(email).setValue(newUser);
    }

    private void setupUIViews() {
        email = (EditText) findViewById(R.id.TvEmailAdd);
        pass = (EditText) findViewById(R.id.TvPass);
        firstName = (EditText) findViewById(R.id.EtFirstName);
        lastName = (EditText) findViewById(R.id.EtLastName);
        registerBtn = (Button) findViewById(R.id.btnReg);

    }

    private Boolean validate() {
        Boolean result = false;

        String emailAdd = email.getText().toString();
        String password = pass.getText().toString();

        if(emailAdd.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT);
        }
        else {
            result = true;
        }

        return result;
    }
}