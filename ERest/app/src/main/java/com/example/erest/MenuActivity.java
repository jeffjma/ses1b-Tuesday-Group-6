package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;


public class MenuActivity extends AppCompatActivity
{


    private Button mBtnOrder;
    private Button mBtnMenuLogout;
    private Button mBtnReservation;
    private Button mBtnChangeReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //button 声明
        //1. button order
        mBtnOrder = findViewById(R.id.btn_tomenu);
        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to order界面
                Intent intent = new Intent(MenuActivity.this,ViewMenu.class);
                startActivity(intent);
            }
        });
        mBtnMenuLogout=findViewById(R.id.btn_menulogout);
        mBtnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mBtnReservation=findViewById(R.id.btn_reservation);
        mBtnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });
        mBtnChangeReservation = findViewById(R.id.btn_change_reservation);
        mBtnChangeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ChangeBookingActivity.class);
                startActivity(intent);
            }
        });
    }
}
