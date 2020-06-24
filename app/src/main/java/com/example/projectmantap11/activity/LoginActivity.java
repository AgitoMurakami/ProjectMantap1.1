package com.example.projectmantap11.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private Button submit;
    private Context context;
    ProgressDialog progressDoalog;
    private EditText pass, id;

    private String bearerkey = "Bearer ";

    private String idasker = "mobileapp";
    private String passasker = "abcd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();

        //login mechanism
        id = findViewById(R.id.userID);
        pass = findViewById(R.id.password);

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

    private String encryptingString (String a) throws UnsupportedEncodingException {
        byte[] data = a.getBytes("UTF-8");
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);

        return base64;
    }
}