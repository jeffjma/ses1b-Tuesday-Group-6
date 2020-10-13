package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
    private Button confirmOrderBtn;
    private Order newOrder = new Order();
    private Spinner sp_menu_type;
    private Spinner sp_booking;
    private EditText et_discount_code;
    private List<String> bookingRef = new ArrayList<String>();
    private User currentUser = new User();
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        userEmail = ((eRestaurantApplication) getApplication()).getUserEmail();

        Log.d("debuggertag", userEmail);

        viewCartBtn = findViewById(R.id.btn_view_order);
        confirmOrderBtn = findViewById(R.id.btn_confirm_order);
        sp_menu_type = findViewById(R.id.sp_menu_type);
        sp_booking = findViewById(R.id.sp_booking);
        et_discount_code = findViewById(R.id.et_discount);

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //Buttons
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });
        viewCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewMenu.this, OrderPopupActivity.class);
                intent.putExtra("food", newOrder.getFood());
                intent.putExtra("price", String.valueOf(newOrder.getPrice()));
                startActivity(intent);
            }
        });

        //When discount code entered
        et_discount_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newOrder.setDiscount(et_discount_code.getText().toString());
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

        updateUserInfo();

        //Array List
        itemList = new ArrayList<>();

        //Clear Array List
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

    }

    private void updateUserInfo() {
        Query query = myRef.child("Users").child(userEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFirstName(snapshot.child("firstName").getValue().toString());
                currentUser.setLastName(snapshot.child("lastName").getValue().toString());
                Log.d("debuggertag", "user info updated!");
                updateBookingSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Read failed");
            }
        });
    }

    private void updateBookingSpinner() {
        Query query = myRef.child("Reservations");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = currentUser.getFirstName()+" "+currentUser.getLastName();
                for(DataSnapshot date: snapshot.getChildren()){
                    for(DataSnapshot time: date.getChildren()){
                        for(DataSnapshot name: time.getChildren()){
                            if(name.getKey().equals(userName)) {
                                bookingRef.add(name.getRef().toString());
                                Log.d("debuggertag", name.getRef().toString());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bookingRef);
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_booking.setAdapter(dataAdaptor);
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

    private void placeOrder() {
        myRef.child("Order").child(newOrder.getUser()).setValue(newOrder);
        Toast.makeText(ViewMenu.this, "Order Placed", Toast.LENGTH_SHORT).show();
        newOrder.clearCart();
    }

    @Override
    public void onItemClick(int position, String Name, String Price) {
        newOrder.addToCart(Name, Price);
        Toast.makeText(ViewMenu.this, Name + " added to order", Toast.LENGTH_SHORT).show();
    }
}