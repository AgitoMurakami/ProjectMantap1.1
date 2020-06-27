package com.example.projectmantap11.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectmantap11.R;
import com.example.projectmantap11.models.Authorization;
import com.example.projectmantap11.services.ApiService;
import com.example.projectmantap11.services.RetrofitClientInstance;
import com.google.android.material.textfield.TextInputEditText;

import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private Button submit;
    private Context context;
    ProgressDialog progressDoalog;
    private TextInputEditText pass, id;

    private String bearerkey = "Bearer ";

    private String idasker = "mobileapp";
    private String passasker = "abcd";

    //permissioncodes
    int permissions_code = 42;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE ,
            Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //prompt permissions
        ActivityCompat.requestPermissions(this,
                permissions,
                permissions_code);

        context = this.getApplicationContext();

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();

        //login mechanism
        id = findViewById(R.id.login_user_id_input);
        pass = findViewById(R.id.login_password_input);

        submit = findViewById(R.id.loginbutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idstring = id.getText().toString();
                String passstring = pass.getText().toString();


                String bkey = idasker+":"+passasker;

                try {
                    bearerkey = encryptingString(bkey);
                    Log.d("YOUR BEARER KEY", "onCreate: " +bearerkey);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.d("ERROR PARSING", "onCreate: " + e);
                }

                //PINDAH
                /*
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);

                 */

                progressDoalog = new ProgressDialog(LoginActivity.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();
                progressDoalog.setCanceledOnTouchOutside(false);

                /*Create handle for the RetrofitInstance interface*/
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "password");
                RequestBody username = RequestBody.create(MediaType.parse("text/plain"), idstring);
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), passstring);
                String authkey = "Basic " + bearerkey;
                Call<Authorization> call = service
                        .loginAccount(authkey,
                                body,
                                username,
                                password);

                try {
                    call.enqueue(new Callback<Authorization>() {
                        @Override
                        public void onResponse(Call<Authorization> call, retrofit2.Response<Authorization> response) {
                            progressDoalog.dismiss();

                            try {
                                Toast.makeText(LoginActivity.this, "" + response.body().getAccessToken(),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                                intent.putExtra("key", response.body().getAccessToken());
                                Log.d("LOGINDATA", "onResponse: " +response.body().getAccessToken());

                                intent.putExtra("name", response.body().getName());
                                Log.d("LOGINDATA", "onResponse: " +response.body().getName().toString());
                                Toast.makeText(getApplicationContext(), "" + response.body().getName(), Toast.LENGTH_SHORT).show();

                                intent.putExtra("uuid", response.body().getUuid());
                                intent.putExtra("employeeid", response.body().getEmployeeID());
                                intent.putExtra("corpname", response.body().getBranchName());
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Authorization> call, Throwable t) {
                            progressDoalog.dismiss();
                            Log.d("FAILURE LOGIN", "onFailure: " + t);
                            Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.e("API ERROR", "onClick login button: ", e );
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage/" +
                            "your internet / " +
                            "your GPS", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private String encryptingString (String a) throws UnsupportedEncodingException {
        byte[] data = a.getBytes("UTF-8");
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);

        return base64;
    }
}