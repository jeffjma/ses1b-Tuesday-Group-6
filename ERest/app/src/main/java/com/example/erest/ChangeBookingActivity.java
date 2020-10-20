package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangeBookingActivity extends AppCompatActivity implements ChangeBookingInterface{

    private RecyclerView recyclerView;
    private ArrayList<ChangeBookingItem> bookingList;
    private ChangeBookingAdapter changeBookingAdapter;
    private DatabaseReference mDatabase;
    private String userEmail;
    private User currentUser = new User();

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

        getDataFromFirebase();
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


    private void getDataFromFirebase() {
        Query query = mDatabase.child("Reservations");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChangeBookingItem changeBookingItem = new ChangeBookingItem();
                for (DataSnapshot date : snapshot.getChildren()) {
                    for (DataSnapshot time : date.getChildren()) {
                        for (DataSnapshot name : time.getChildren()) {
                            if(name.getKey().equals(currentUser.firstName + " " + currentUser.lastName)){
                                changeBookingItem.setDate(date.getKey());
                                changeBookingItem.setTime(time.getKey());
                                bookingList.add(changeBookingItem);
                                updateRecyclerView();
                            }
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
    public void onOrderButtonClick(int position, String date, String time) {
        
    }
}