package com.example.erest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewStaff extends AppCompatActivity {

    private static final String TAG = "viewStaff";

    RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<Staff> staffList;
    private StaffAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_staff);

        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //Array List
        staffList = new ArrayList<>();

        //Clear Array List
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {

        Query query = myRef.child("Staff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Staff staff = new Staff();

                    staff.setName(ds.child("Name").getValue().toString());
                    staff.setPrice(ds.child("Email").getValue().toString());
                    staff.setPosition(ds.child("Position").getValue().toString());

                    staffList.add(staff);
                }

                recyclerAdapter = new StaffAdapter(getApplicationContext(), staffList);
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
        if(staffList != null){
            staffList.clear();

            if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        staffList = new ArrayList<>();
    }
}