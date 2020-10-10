package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationActivity extends AppCompatActivity{


    int day, month, year;

    private Spinner pax;
    private EditText et_time;
    private EditText et_date;
    private final Calendar mCalender = Calendar.getInstance();
    private Button mbtn_submit;
    private String userEmail;
    private User currentUser = new User();
    private int currCapacity = 0;
    private Date rightNow;
    private Date bookingDate;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation);

        userEmail = ((eRestaurantApplication) getApplication()).getUserEmail();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        updateUserInfo();

        rightNow = Calendar.getInstance().getTime();
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
                new TimePickerDialog(ReservationActivity.this, android.R.style.Theme_Holo_Light_Dialog, time, mCalender.get(Calendar.HOUR), mCalender.get(Calendar.MINUTE), false).show();
                updateCapacity(et_date.getText().toString(), et_time.getText().toString());
            }
        });
        mbtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReservation(currentUser.getFirstName() + " " + currentUser.getLastName(), pax.getSelectedItem().toString(), et_date.getText().toString(), et_time.getText().toString());
            }
        });

    }

    private void updateUserInfo() {
        Query query = mDatabase.child("Users").child(userEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFirstName(snapshot.child("firstName").getValue().toString());
                currentUser.setLastName(snapshot.child("lastName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Read failed");
            }
        });
    }

    private void createReservation(String name, String pax, String selectedDate, String selectedTime){
        if(bookingDate.after(rightNow)) {
            if (!maxCapacity(pax, selectedDate, selectedTime)) {
                Reservation reservation = new Reservation(pax);

                mDatabase.child("Reservations").child(selectedDate).child(selectedTime).child(name).setValue(reservation);
                Toast.makeText(ReservationActivity.this, "Booking successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ReservationActivity.this, MenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ReservationActivity.this, "Selected time is fully booked, please select a different time.", Toast.LENGTH_LONG).show();
                updateCapacity(selectedDate, selectedTime);
            }
        }
        else {
            Toast.makeText(ReservationActivity.this, "Can't make a booking in the past! Please pick a different date/time.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean maxCapacity(String pax, String date, String time) {

        currCapacity += Integer.parseInt(pax);

        if(currCapacity > 20) {
            Log.d("debuggertag", "FINAL CHECK: current capacity is " + currCapacity);
            return true;
        }
        else {
            return false;
        }
    }

    private void updateCapacity(String date, String time) {
        if(date.isEmpty() || time.isEmpty()){
            Log.d("debuggertag", "not fetching, empty fields");
            return;
        }
        else {
            Query query = mDatabase.child("Reservations").child(date).child(time);

            currCapacity = 0;
            Log.d("debuggertag", "INITAL CHECK: current capacity is " + currCapacity);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        currCapacity += Integer.parseInt(ds.child("pax").getValue().toString());
                        Log.d("debuggertag", "current capacity is " + currCapacity);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            mCalender.set(Calendar.HOUR_OF_DAY, hour);
            mCalender.set(Calendar.MINUTE, min);
            bookingDate = mCalender.getTime();
            updateTime();
            updateCapacity(et_date.getText().toString(), et_time.getText().toString());
        }
    };

    private void updateTime() {
        String myFormat = "hh:mm aaa";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et_time.setText(sdf.format(mCalender.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, monthOfYear);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            bookingDate = mCalender.getTime();
            updateDate();
            updateCapacity(et_date.getText().toString(), et_time.getText().toString());
        }
    };

    private void updateDate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et_date.setText(sdf.format(mCalender.getTime()));
    }

}
