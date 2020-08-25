package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class MenuActivity extends AppCompatActivity
{

    private Button mBtnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);
        //button 声明
        //1. button order
        mBtnOrder = findViewById(R.id.btn_orderid);
        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to order界面
                Intent intent = new Intent(MenuActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
