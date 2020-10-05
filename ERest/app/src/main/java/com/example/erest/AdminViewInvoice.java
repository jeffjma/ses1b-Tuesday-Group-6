package com.example.erest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewInvoice extends AppCompatActivity {

    private static final String TAG = "viewMenu";

    RecyclerView recyclerView;
    private Button mbtn_back;

    //Firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<AdminInvoiceItem> itemList;
    private AdminInvoiceAdapter adminInvoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_invoice);

        mbtn_back=findViewById(R.id.btn_invoiceback);
        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminViewInvoice.this,AdminMenu.class);
                startActivity(intent);
            }
        });

        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.invoicerecycle);
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

        Query query = myRef.child("Order");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot ds: snapshot.getChildren()){
                    AdminInvoiceItem items = new AdminInvoiceItem();

                    items.setId(ds.child("id").getValue().toString());
                    items.setName(ds.child("user").getValue().toString());
                    items.setPrice(ds.child("price").getValue().toString());
                    items.setFood(ds.child("food").getValue().toString());
                    items.setDiscount(ds.child("discount").getValue().toString());

                    itemList.add(items);
                }

                adminInvoiceAdapter = new AdminInvoiceAdapter(getApplicationContext(), itemList);
                recyclerView.setAdapter(adminInvoiceAdapter);
                adminInvoiceAdapter.notifyDataSetChanged();
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

            if(adminInvoiceAdapter != null) {
                adminInvoiceAdapter.notifyDataSetChanged();
            }
        }

        itemList = new ArrayList<>();
    }



}

