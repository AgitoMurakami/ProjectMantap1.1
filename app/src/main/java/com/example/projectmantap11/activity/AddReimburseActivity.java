package com.example.projectmantap11.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmantap11.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddReimburseActivity extends AppCompatActivity {

    String currentPhotoPath;
    String currentGalleryPath;
    String sendPath = "your filepath here";
    String ocrtext;

    InputImage image;

    private Button submitPictButton;

    TextRecognizer recognizer;

    private ImageView leftRotation, rightRotation, killActivityButton, showImage;

    private int rotationRadius = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reimburse);

        showImage = findViewById(R.id.showimage);
        killActivityButton = findViewById(R.id.killactivity);

        killActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        currentPhotoPath = getIntent().getStringExtra("imagepath");
        currentGalleryPath = getIntent().getStringExtra("gallerypath");

        Toast.makeText(this, "TEST :" + currentGalleryPath,Toast.LENGTH_SHORT).show();
        Log.d("TEST", "onCreate: " + currentGalleryPath);


        if (currentGalleryPath == null) {
            galleryAddPic(currentPhotoPath);
            setPic(currentPhotoPath);
            Log.d("GALLERY", "onCreate: " + currentPhotoPath);

        } else {

            try {
                setPic(currentGalleryPath);

            } catch (Exception e) {
                Log.e("ADD REIMBURSE", "get data: " + e);
                Toast.makeText(this, "That was not a picture, try again", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        //rotation button
        leftRotation = findViewById(R.id.leftrotationbutton);
        rightRotation = findViewById(R.id.rightrotationbutton);

        leftRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationRadius = (rotationRadius + 90) % 360;
                showImage.setRotation(rotationRadius);
            }
        });

        rightRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationRadius = (rotationRadius - 90) % 360;
                showImage.setRotation(rotationRadius);
            }
        });

        getSupportActionBar().hide();

        //submit button
        submitPictButton = findViewById(R.id.submitbutton);
        submitPictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                String fname = "Image"+".jpg";
                File file = new File (myDir, fname);
                if (file.exists ()) file.delete ();

                sendPath = file.getAbsolutePath();
                try {
                    Bitmap xBitmap = ((BitmapDrawable)showImage.getDrawable()).getBitmap();
                    Bitmap finalBitmap = rotate(xBitmap, rotationRadius);

                    //set OCR image
                    image = InputImage.fromBitmap(finalBitmap, 0);

                    FileOutputStream out = new FileOutputStream(file);
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), " " + e, Toast.LENGTH_SHORT ).show();
                }

                //runOCR
                getOCRText();
            }
        });
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
        showImage.setImageBitmap(bitmap);
    }

    public static Bitmap rotate(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),source.getHeight(), matrix, false);
    }

    private void getOCRText(){
        //setup recognizer
        recognizer = TextRecognition.getClient();

        //setuptext
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                //Toast.makeText(getApplicationContext(), "hasil : " + visionText.getText(), Toast.LENGTH_SHORT).show();
                                ocrtext = visionText.getText();

                                if (sendPath != null) {
                                    Intent intent = new Intent(AddReimburseActivity.this, AddReimbursmentDetailActivity.class);
                                    intent.putExtra("picture", sendPath);
                                    intent.putExtra("description", ocrtext);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Your filepath is null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        ocrtext = "failed to get description" + e;
                                    }
                                });
    }

}