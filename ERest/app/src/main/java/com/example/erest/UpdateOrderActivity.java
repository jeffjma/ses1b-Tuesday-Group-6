package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateOrderActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Double price, discountAmount;
    private TextView tv_price;
    private TextView tv_discount;
    private Order currOrder;
    private CompleteOrderAdapter completeOrderAdapter;
    private Button changeOrderBtn, cancelOrderBtn;
    private Spinner sp_booking;
    private String userEmail;
    private User currentUser = new User();
    private List<String> bookingRef = new ArrayList<String>();
    private EditText et_discount_code;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        userEmail = ((eRestaurantApplication) getApplication()).getUserEmail();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tv_price = findViewById(R.id.tv_order_total);
        tv_discount = findViewById(R.id.tv_order_discount);
        changeOrderBtn = findViewById(R.id.btn_update_order);
        cancelOrderBtn = findViewById(R.id.btn_cancel_order);

        updateUserInfo();

        RecyclerView recyclerView;
        // RecyclerView Setup
        recyclerView = (RecyclerView) findViewById(R.id.rv_order);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        completeOrderAdapter = new CompleteOrderAdapter(getApplicationContext(), currOrder.getFood());
        recyclerView.setAdapter(completeOrderAdapter);
        completeOrderAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Read failed");
            }
        });
    }

    private void updateOrderInfo() {
        Query query = mDatabase.child("Order").child(currOrder.getUser());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    tv_discount.setText("Discount: $" + currOrder.getDiscountAmount());
                }
                else {
                    Toast.makeText(UpdateOrderActivity.this, "Invalid discount code entered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}