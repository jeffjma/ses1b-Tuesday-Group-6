package com.example.erest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class AdminInvoiceDetail extends AppCompatActivity {
    TextView name;
    TextView id;
    TextView food;
    TextView price;
    TextView discount;
    Button mbtnback;
    Button mbtnprint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_invoice_detail);
        name=findViewById(R.id.namedetails);
        id=findViewById(R.id.iddetails);
        price=findViewById(R.id.pricedetails);
        food=findViewById(R.id.fooddetails);
        discount=findViewById(R.id.discountdetails);

        String nameextra=getIntent().getStringExtra("Name");
        String idextra=getIntent().getStringExtra("ID");
        String priceextra=getIntent().getStringExtra("Price");
        String foodextra=getIntent().getStringExtra("Food");
        String discountextra=getIntent().getStringExtra("Discount");

        priceextra=priceextra.replaceAll("[^0-9?!\\.]","");
        String discountextra2=discountextra.replaceAll("[^0-9?!\\.]","");
        double discountextra3= Integer.parseInt(discountextra2);
        double priceextra2= Integer.parseInt(priceextra);

        double priceextra3=priceextra2*(100-discountextra3)/100;
        String priceextra4= String.valueOf(priceextra3);

        name.setText(nameextra);
        id.setText(idextra);
        price.setText("$"+priceextra4);
        food.setText(foodextra);
        discount.setText(discountextra);


        mbtnback=findViewById(R.id.backdetails);
        mbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminInvoiceDetail.this, AdminViewInvoice.class);
                startActivity(intent);
            }
        });


        mbtnprint=findViewById(R.id.printdetails);
        mbtnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });


    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }



}