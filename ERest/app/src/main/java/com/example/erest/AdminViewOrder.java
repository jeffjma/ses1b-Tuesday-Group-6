package com.example.erest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewOrder extends AppCompatActivity{
    private static final String TAG = "viewmenu";

    RecyclerView recyclerView;

    private DatabaseReference myRef;


    private ArrayList<AdminViewOrderItem> itemList;
    private AdminViewOrderAdapter adminViewOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_order);


        recyclerView = (RecyclerView) findViewById(R.id.OrderRecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference();
        itemList = new ArrayList<>();
        ClearAll();
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {

        final Query query = myRef.child("Order");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot cust: snapshot.getChildren()){
                    for(DataSnapshot order: cust.getChildren()) {
                        AdminViewOrderItem items = new AdminViewOrderItem();
                        String food = "Food: \n";
                        double price = Double.parseDouble(order.child("price").getValue().toString());
                        double discountAmount =  Double.parseDouble(order.child("discountAmount").getValue().toString());
                        price -= discountAmount;
                        items.setUser("Customer Name: " + order.child("user").getValue().toString());
                        items.setPrice("$"+price);
                        for(DataSnapshot meals: order.child("food").getChildren()) {
                            food += meals.child("name").getValue().toString() + "\n";
                        }
                        items.setFood(food);

                        itemList.add(items);
                    }
                }

                adminViewOrderAdapter = new AdminViewOrderAdapter(getApplicationContext(), itemList);
                recyclerView.setAdapter(adminViewOrderAdapter);
                adminViewOrderAdapter.notifyDataSetChanged();
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ClearAll() {
        if(itemList != null){
            itemList.clear();

            if(adminViewOrderAdapter != null) {
                adminViewOrderAdapter.notifyDataSetChanged();
            }
        }

        itemList = new ArrayList<>();
    }
}
