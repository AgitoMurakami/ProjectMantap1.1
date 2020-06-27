package com.example.projectmantap11.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectmantap11.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReimbursementDetailActivity extends AppCompatActivity {

    TextView nameTV, reimburseIDTV, invoiceIDTV, categoryTV, amountTV, noteTV, descriptionTV;
    ImageView receiptPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reimbursement_detail);

        //hide actionbar
        getSupportActionBar().hide();

        //findview;
        nameTV = findViewById(R.id.reimburse_detail_name);
        reimburseIDTV = findViewById(R.id.reimburse_detail_id);
        invoiceIDTV = findViewById(R.id.reimburse_detail_invoice_id);
        categoryTV = findViewById(R.id.reimburse_detail_category);
        amountTV = findViewById(R.id.reimburse_detail_money);
        noteTV = findViewById(R.id.reimburse_detail_note);
        descriptionTV = findViewById(R.id.reimburse_detail_description);
        receiptPicture = findViewById(R.id.reimburse_detail_photo);

        //get intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String reimburseID = intent.getStringExtra("reimburseID");
        String invoiceID = intent.getStringExtra("invoiceID");
        String category = intent.getStringExtra("category");
        String amount = intent.getStringExtra("amount");
        String note = intent.getStringExtra("note");
        String description = intent.getStringExtra("description");
        String filepath = intent.getStringExtra("filepath");

        //settext
        nameTV.setText(name);
        reimburseIDTV.setText(reimburseID);
        invoiceIDTV.setText(invoiceID);
        categoryTV.setText(category);
        amountTV.setText(amount);
        noteTV.setText(note);
        descriptionTV.setText(description);

        //set image
        Picasso.get().load(filepath).into(receiptPicture);
    }
}