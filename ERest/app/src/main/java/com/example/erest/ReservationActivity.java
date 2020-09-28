package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationActivity extends AppCompatActivity{


    int day, month, year;

    private EditText et_name;
    private Spinner pax;
    private EditText et_time;
    private EditText et_date;
    private final Calendar mCalender = Calendar.getInstance();
    private final Calendar mTime = Calendar.getInstance();
    private Button mbtn_submit;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        et_name = findViewById(R.id.et_name);
        pax = (Spinner) findViewById(R.id.pax);
        et_time = findViewById(R.id.et_time_slot);
        et_time.setInputType(InputType.TYPE_NULL);
        et_date = findViewById(R.id.et_date);
        mbtn_submit = findViewById(R.id.btn_submit);

        List<String> numPax = new ArrayList<String>();
        numPax.add("1");
        numPax.add("2");
        numPax.add("3");
        numPax.add("4");
        numPax.add("5");
        numPax.add("6");
        numPax.add("7");
        numPax.add("8");
        numPax.add("9");
        numPax.add("10");

        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numPax);
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pax.setAdapter(dataAdaptor);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReservationActivity.this, date,mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(ReservationActivity.this, android.R.style.Theme_Holo_Light_Dialog, time, mTime.get(Calendar.HOUR), mTime.get(Calendar.MINUTE), false).show();

            }
        });
        mbtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReservation(et_name.getText().toString(), pax.getSelectedItem().toString(), et_date.getText().toString(), et_time.getText().toString());
                Toast.makeText(ReservationActivity.this,"Booking successful",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ReservationActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createReservation(String name, String pax, String selectedDate, String selectedTime){
        Reservation reservation = new Reservation(pax, selectedDate, selectedTime);

        mDatabase.child("Reservations").child(name).setValue(reservation);
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            mTime.set(Calendar.HOUR_OF_DAY, hour);
            mTime.set(Calendar.MINUTE, min);
            updateTime();
        }
    };

    private void updateTime() {
        String myFormat = "hh:mm aaa";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et_time.setText(sdf.format(mTime.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, monthOfYear);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();

        }
    };

    private void updateDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et_date.setText(sdf.format(mCalender.getTime()));
    }

}
