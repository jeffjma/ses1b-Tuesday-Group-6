package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMenu extends AppCompatActivity implements ViewMenuInterface{
    private static final String TAG = "viewMenu";

    RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<MenuItem> itemList;
    private RecyclerAdapter recyclerAdapter;
    private Button viewCartBtn;
    private Button confirmOrderBtn;
    private Order newOrder = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        viewCartBtn = findViewById(R.id.btn_vieworder);
        confirmOrderBtn = findViewById(R.id.btn_confirm_order);

        //Buttons
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });
        
        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //Array List
        itemList = new ArrayList<>();

        //Clear Array List
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {

        Query query = myRef.child("Menu");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot ds: snapshot.getChildren()){
                    MenuItem items = new MenuItem();

                    items.setName(ds.child("Name").getValue().toString());
                    items.setPrice(ds.child("Price").getValue().toString());

                    itemList.add(items);
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), itemList, ViewMenu.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
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

            if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        itemList = new ArrayList<>();
    }

    private void placeOrder() {
        myRef.child("Order").child(newOrder.getUser()).setValue(newOrder);
        Toast.makeText(ViewMenu.this, "Order Placed", Toast.LENGTH_SHORT).show();
        newOrder.clearCart();
    }

    @Override
    public void onItemClick(int position, String name, String price) {
        newOrder.addToCart(name, price);
        Toast.makeText(ViewMenu.this, name + " added to order", Toast.LENGTH_SHORT).show();
    }
}