package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMenu extends AppCompatActivity implements ViewMenuInterface{
    private static final String TAG = "viewMenu";

    RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<MenuItem> itemList;
    private RecyclerAdapter recyclerAdapter;
    private Button viewCartBtn;
    private Order newOrder = new Order();
    private Spinner sp_menu_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        viewCartBtn = findViewById(R.id.btn_view_order);
        sp_menu_type = findViewById(R.id.sp_menu_type);

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //Buttons
        viewCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debuggertag", ""+newOrder.getPrice());
                Intent intent = new Intent(ViewMenu.this, CompleteOrderActivity.class);
                intent.putExtra("Order", newOrder);
                startActivity(intent);

            }
        });

        //Spinner for menu type
        List<String> menu = new ArrayList<>();
        menu.add("Entree");
        menu.add("Mains");
        menu.add("Dessert");

        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menu);
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_menu_type.setAdapter(menuAdapter);
        sp_menu_type.setSelection(0);

        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Array List
        itemList = new ArrayList<>();

        //Clear Array List
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

        //Update Menu Type Display
        sp_menu_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GetDataFromFirebase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void GetDataFromFirebase() {

        Query query = myRef.child("Menu").child(sp_menu_type.getSelectedItem().toString());

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

    private void addToCart(String name) {
        Query query = myRef.child("Menu");
        final String itemName = name;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot menuType: snapshot.getChildren()) {
                    for(DataSnapshot ds: menuType.getChildren()) {
                        if (ds.child("Name").getValue().toString().equals(itemName)) {
                            MenuItem item = new MenuItem();
                            item.setName(ds.child("Name").getValue().toString());
                            item.setPrice(ds.child("Price").getValue().toString());
                            newOrder.addToCart(item);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position, String Name) {
        addToCart(Name);
        Toast.makeText(ViewMenu.this, Name + " added to order", Toast.LENGTH_SHORT).show();
    }
}