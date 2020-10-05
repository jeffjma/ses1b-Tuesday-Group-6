package com.example.erest;

import android.os.Bundle;

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

    //Firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<AdminInvoiceItem> itemList;
    private AdminInvoiceAdapter adminInvoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_invoice);

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

                    items.setId(ds.child("Id").getValue().toString());
                    items.setName(ds.child("User").getValue().toString());
                    items.setPrice(ds.child("Price").getValue().toString());
                    items.setFood(ds.child("Food").getValue().toString());
                    items.setDiscount(ds.child("Discount").getValue().toString());

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

