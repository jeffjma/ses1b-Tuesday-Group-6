package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChangeBookingActivity extends AppCompatActivity implements ChangeBookingInterface{

    private RecyclerView recyclerView;
    private ArrayList<ChangeBookingItem> bookingList;
    private ChangeBookingAdapter changeBookingAdapter;
    private DatabaseReference mDatabase;
    private String userEmail;
    private User currentUser = new User();
    private String fullName;
    private Calendar mCalendar = Calendar.getInstance();
    private Calendar bookingCheckCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_booking);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        userEmail = ((eRestaurantApplication) getApplication()).getUserEmail();

        updateUserInfo();

        recyclerView = (RecyclerView) findViewById(R.id.rv_bookings);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        bookingList = new ArrayList<>();

        bookingCheckCalendar.add(Calendar.DATE, +1);

        getDataFromFirebase();


        Log.d("debuggertag", mCalendar.getTime().toString());
    }

    private void updateUserInfo() {
        Query query = mDatabase.child("Users").child(userEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFirstName(snapshot.child("firstName").getValue().toString());
                currentUser.setLastName(snapshot.child("lastName").getValue().toString());
                fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Read failed");
            }
        });
    }


    private void getDataFromFirebase() {
        Query query = mDatabase.child("Reservations");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot date : snapshot.getChildren()) {
                    for (DataSnapshot time : date.getChildren()) {
                        for (DataSnapshot name : time.getChildren()) {
                            if(name.getKey().equals(currentUser.firstName + " " + currentUser.lastName)){
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                    mCalendar.setTime(sdf.parse(date.getKey()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(!mCalendar.getTime().before(bookingCheckCalendar.getTime())) {
                                    ChangeBookingItem changeBookingItem = new ChangeBookingItem();
                                    changeBookingItem.setDate(date.getKey());
                                    changeBookingItem.setTime(time.getKey());
                                    bookingList.add(changeBookingItem);
                                }
                            }
                            updateRecyclerView();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateRecyclerView() {
        ChangeBookingAdapter changeBookingAdapter = new ChangeBookingAdapter(getApplicationContext(), bookingList, ChangeBookingActivity.this);
        recyclerView.setAdapter(changeBookingAdapter);
        changeBookingAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onBookingButtonClick(int position, String date, String time, int pax) {
        Intent intent = new Intent(ChangeBookingActivity.this, UpdateBookingActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("pax", pax);
        startActivity(intent);
    }

    @Override
    public void onOrderButtonClick(int position,String date, String time) {
        Query query = mDatabase.child("Order").child(fullName);
        final String dateTime = date + " " + time;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    Toast.makeText(ChangeBookingActivity.this, "Booking doesn't have an order.", Toast.LENGTH_SHORT).show();
                }
                int i = 0;
                for(DataSnapshot order: snapshot.getChildren()) {
                    if(order.child("reservation").getValue().toString().equals(dateTime)){
                        Log.d("debuggertag", order.getRef().toString());
                        order.getRef().removeValue();
                        Toast.makeText(ChangeBookingActivity.this, "Order successfully cancelled!", Toast.LENGTH_SHORT).show();
                    break;
                    }
                    if(++i == snapshot.getChildrenCount()) {
                        Toast.makeText(ChangeBookingActivity.this, "Booking doesn't have an order.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}