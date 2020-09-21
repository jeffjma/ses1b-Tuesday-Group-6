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

        //Login Button when clicked run validation method
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_username = mEtEmail.getText().toString();
                str_password = mEtPassword.getText().toString();

                if ( str_username.length()<3 || !str_username.contains("@"))
                {
                    Toast.makeText(MainActivity.this,"e-mail incorrect",Toast.LENGTH_LONG).show();
                }
                else if(str_password.length()<3)
                {
                    Toast.makeText(MainActivity.this,"password should more than three",Toast.LENGTH_LONG).show();
                }
                else
                {
                    validate(str_username, str_password);
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
    //validation method
    private void validate (String userEmail, String userPassword) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(str_username.equals("admin@test.com") && str_password.equals("123456"))
                    {
                        startActivity(new Intent(MainActivity.this,AdminMenu.class));
                    }
                    else
                    {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Username or Password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}