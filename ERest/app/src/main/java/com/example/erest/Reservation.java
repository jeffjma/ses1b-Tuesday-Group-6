package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class Reservation extends AppCompatActivity {

    private EditText met_description;
    private Button mbtn_submit;
    private String description_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation);

        met_description=findViewById(R.id.et_description);
        mbtn_submit = findViewById(R.id.btn_submit);
        mbtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description_details = met_description.getText().toString();
                Intent intent = new Intent(Reservation.this,MenuActivity.class);
                startActivity(intent);
            }
        });


    }



}
