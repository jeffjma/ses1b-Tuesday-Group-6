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
import android.view.View;
import android.view.Window;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompleteOrderActivity extends AppCompatActivity implements CompleteOrderInterface{

    private DatabaseReference mDatabase;
    private Double price, discountAmount;
    private TextView tv_price;
    private TextView tv_discount;
    private Order currOrder;
    private CompleteOrderAdapter completeOrderAdapter;
    private Button confirmOrderBtn;
    private Spinner sp_booking;
    private String userEmail;
    private User currentUser = new User();
    private List<String> bookingRef = new ArrayList<String>();
    private EditText et_discount_code;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complete_order);

        userEmail = ((eRestaurantApplication) getApplication()).getUserEmail();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        et_discount_code = findViewById(R.id.et_discount);
        tv_price = findViewById(R.id.tv_order_total);
        tv_discount = findViewById(R.id.tv_order_discount);
        confirmOrderBtn = findViewById(R.id.btn_confirm_order);
        sp_booking = findViewById(R.id.sp_booking);

        updateUserInfo();

        RecyclerView recyclerView;

        Bundle bundle = getIntent().getExtras();
        currOrder = (Order) bundle.getSerializable("Order");
        Log.d("debuggertag", ""+currOrder.getPrice());

        //RecyclerView Setup
        recyclerView = (RecyclerView) findViewById(R.id.rv_order);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        completeOrderAdapter = new CompleteOrderAdapter(getApplicationContext(), currOrder.getFood(), CompleteOrderActivity.this);
        recyclerView.setAdapter(completeOrderAdapter);
        completeOrderAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        //Button
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });

        //When discount code entered
        et_discount_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    currOrder.setDiscount(et_discount_code.getText().toString());
                    updateDiscount(currOrder.getDiscount());
                }
            }
        });

        price = currOrder.getPrice();
        discountAmount = currOrder.getDiscountAmount();

        tv_price.setText("Total Price: $" + (price - discountAmount));
        tv_discount.setText("Discount: $" + discountAmount);
        sp_booking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debuggertag", "BOOKING DATE: " + sp_booking.getSelectedItem().toString());
                currOrder.setReservation(sp_booking.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private  void updateTotal(int position, double price) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems = currOrder.getFood();
        menuItems.remove(position-1);
        currOrder.setFood(menuItems);
        currOrder.setPrice(currOrder.getPrice()-price);
        price = currOrder.getPrice();
        updateDiscount(currOrder.getDiscount());
    }

    private void countChildren() {
        Query query = mDatabase.child("Order").child(currentUser.getFirstName() + " " + currentUser.getLastName());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void placeOrder() {
        mDatabase.child("Order").child(currOrder.getUser()).child("0" + count).setValue(currOrder);
        Toast.makeText(CompleteOrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CompleteOrderActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void updateBookingSpinner() {
        Query query = mDatabase.child("Reservations");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = currentUser.getFirstName()+" "+currentUser.getLastName();
                for(DataSnapshot date: snapshot.getChildren()){
                    for(DataSnapshot time: date.getChildren()){
                        for(DataSnapshot name: time.getChildren()){
                            if(name.getKey().equals(userName)) {
                                String bookingDate = name.getRef().toString();
                                bookingDate = bookingDate.substring(54, 75);
                                bookingDate = bookingDate.replaceAll("/", " ");
                                bookingDate = bookingDate.replaceAll("%3A", ":");
                                bookingDate = bookingDate.replaceAll("%20", " ");
                                bookingDate = bookingDate.trim();
                                bookingRef.add(bookingDate);
                                Log.d("debuggertag", bookingDate);
                                tester();
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

    private void tester() {
        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bookingRef);
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_booking.setAdapter(dataAdaptor);
        sp_booking.setSelection(0);
    }

    private void updateUserInfo() {
        Query query = mDatabase.child("Users").child(userEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFirstName(snapshot.child("firstName").getValue().toString());
                currentUser.setLastName(snapshot.child("lastName").getValue().toString());
                Log.d("debuggertag", "user info updated!");
                currOrder.setUser(currentUser.getFirstName() + " " + currentUser.getLastName());
                updateBookingSpinner();
                countChildren();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Read failed");
            }
        });
    }

    private void updateDiscount(final String discountCode) {
        Query query = mDatabase.child("Discounts");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(discountCode)) {
                    currOrder.setDiscountAmount(
                            currOrder.getPrice() *
                                    Double.parseDouble(snapshot.child(discountCode).child("discount").getValue().toString()));
                    tv_price.setText("Total Price: $" + (price - discountAmount));
                    tv_discount.setText("Discount: $" + currOrder.getDiscountAmount());
                }
                else {
                    Toast.makeText(CompleteOrderActivity.this, "Invalid discount code entered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDataChange(int position, String price) {
        double doublePrice = Double.parseDouble(price.substring(1));
        updateTotal(position, doublePrice);
    }
}