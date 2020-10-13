package com.example.erest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

public class OrderPopupActivity extends AppCompatActivity {

    private String food, price;
    private TextView tv_food;
    private TextView tv_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        tv_food = findViewById(R.id.tv_order_items);
        tv_price = findViewById(R.id.tv_order_total);

        food = getIntent().getStringExtra("food");
        price = getIntent().getStringExtra("price");

        food.replaceAll("\\s", "\n");

        tv_food.setText(food);
        tv_price.setText("Total Price: $" + price);
    }
}