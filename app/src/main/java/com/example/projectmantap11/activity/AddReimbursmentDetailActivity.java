package com.example.projectmantap11.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectmantap11.R;

import java.io.File;

public class AddReimbursmentDetailActivity extends AppCompatActivity {

    private ImageView loadRotateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reimbursment_detail);


        loadRotateImage = findViewById(R.id.showimagefromrotate);
        Bundle extras = getIntent().getExtras();
        String path = extras.getString("picture");
        String ocr = extras.getString("description");
        Toast.makeText(this, "OCR TEXTS : " + ocr, Toast.LENGTH_SHORT).show();

        //galleryAddPic(path);
        setPic(path);
    }

    private void galleryAddPic( String n) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(n);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void setPic(String n) {

        // Get the dimensions of the View

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int height = 800;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int ratio = photoH / height;
        int width = photoW * ratio;


        Log.d("UKURAN", "setPic: " + photoH + " , " + photoW);

        // Determine how much to scale down the image
        int scaleFactor = Math.min(width, height);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(n, bmOptions);
        loadRotateImage.setImageBitmap(bitmap);
    }
}