package com.example.erest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdminViewReservation extends AppCompatActivity implements OnItemClickInterface {


    RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<AdminViewReservationItem> itemList;
    private TextView tv_date;
    private final Calendar mCalender = Calendar.getInstance();
    private AdminViewReservationAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationview);

        tv_date = (TextView) findViewById(R.id.tv_date);

        updateDate();


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminViewReservation.this, date, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference();


        //Array List
        itemList = new ArrayList<>();

        //Clear Array List
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();
    }
    private void GetDataFromFirebase() {

        Query query = myRef.child("Reservations").child(tv_date.getText().toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot ds: snapshot.getChildren()){
                    AdminViewReservationItem items = new AdminViewReservationItem();

                    items.setTime(ds.getKey());
                    for(DataSnapshot n: ds.getChildren()){
                        items.setName(n.getKey());
                        items.setPax(n.child("pax").getValue().toString());
                        itemList.add(items);
                    }

                }
                recyclerAdapter = new AdminViewReservationAdapter(getApplicationContext(), itemList, AdminViewReservation.this);
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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, monthOfYear);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
            GetDataFromFirebase();
        }
    };

    private void updateDate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        tv_date.setText(sdf.format(mCalender.getTime()));
    }

    @Override
    public void onClick(int position, String time, String name) {
        Intent intent = new Intent(AdminViewReservation.this, AdminViewOrder.class);
        intent.putExtra("date", tv_date.getText().toString());
        intent.putExtra("time", time);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}