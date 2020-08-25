package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class OrderActivity extends AppCompatActivity {


    private ListView mLv_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);



        mLv_order= findViewById(R.id.lv_menu);
        mLv_order.setAdapter(new OrderAdapter(OrderActivity.this));

    }
}