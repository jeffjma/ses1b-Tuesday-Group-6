package com.example.erest;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private Button mBtnReg;
    private TextView mTvTitle;
    private EditText mEtEmail;
    private EditText mEtPassword;

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

        //Login Button when clicked run validation method
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(mEtEmail.getText().toString(), mEtPassword.getText().toString());
            }
        });
    }
    //validation method
    private void validate (String userEmail, String userPassword) {
        if((userEmail.equals("test@gmail.com")) && (userPassword.equals("password"))) {
            Intent intent = new Intent(MainActivity.this,MenuActivity.class);
            startActivity(intent);
        }
    }

}