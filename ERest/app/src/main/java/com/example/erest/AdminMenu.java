package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class AdminMenu extends AppCompatActivity{

    private Button mBtnAdminLogout;
    private Button mBtnAdminToOrder;
    private Button mBtnAdminToRes;
    private Button mBtnAdminToInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);

        mBtnAdminLogout=findViewById(R.id.btn_admlogout);
        mBtnAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mBtnAdminToOrder=findViewById(R.id.btn_vieworder);
        mBtnAdminToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,AdminViewOrder.class);
                startActivity(intent);
            }
        });
        mBtnAdminToRes=findViewById(R.id.btn_viewreservation);
        mBtnAdminToRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,AdminViewReservation.class);
                startActivity(intent);
            }
        });
        mBtnAdminToInvoice=findViewById(R.id.btn_viewinvoice);
        mBtnAdminToInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,AdminViewInvoice.class);
                startActivity(intent);
            }
        });
    }
}
