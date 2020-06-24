package com.example.projectmantap11.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.example.projectmantap11.BuildConfig;
import com.example.projectmantap11.R;
import com.example.projectmantap11.activity.AddReimburseActivity;
import com.example.projectmantap11.services.ImageFilePath;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private LinearLayout cameraLaunch;
    private LinearLayout galleryLaunch;

    private String TAG = "BOTTOM SHEET";

    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST_TAKE_PHOTO = 1;

    static final int GALLERY_REQUEST_CODE = 222;

    private String currentPhotoPath;


    public BottomSheetFragment() {
        // Required empty public constructor
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_layout, container, false);
        cameraLaunch = view.findViewById(R.id.cameraoption);
        galleryLaunch = view.findViewById(R.id.galleryoption);

        cameraLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        galleryLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.dualPane);
        //fragment.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: kode data " + requestCode);

        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: " + requestCode);
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:

                    Intent in1 = new Intent(getActivity(), AddReimburseActivity.class);
                    in1.putExtra("imagepath", currentPhotoPath);
                    startActivity(in1);
                    dismiss();
                    break;

                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    String realpath = ImageFilePath.getPath(getActivity(), data.getData());



                    //String imgDecodableString = cursor.getString(columnIndex);
                    Intent in2 = new Intent(getActivity(), AddReimburseActivity.class);
                    in2.putExtra("gallerypath", realpath);
                    Log.d(TAG, "onActivityResult: TO ADD REIMBURSE");
                    startActivity(in2);

                    dismiss();
                    break;

                    /*

                    //build version for segmented path, haven't implemented yet
                    if (Build.VERSION.SDK_INT >= 29) {
                        Intent in2 = new Intent(getActivity(), AddReimburseActivity.class);
                        in2.putExtra("gallerypath", imgDecodableString);
                        startActivity(in2);
                        dismiss();
                    } else {
                        Intent in2 = new Intent(getActivity(), AddReimburseActivity.class);
                        in2.putExtra("gallerypath", imgDecodableString);
                        startActivity(in2);
                        dismiss();
                    }

                     */
            }
        } else {
            Toast.makeText(getActivity(), "No Image Taken", Toast.LENGTH_SHORT).show();
        }

    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Failed to save file", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        intent.setType("image/*");

        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png","image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

}