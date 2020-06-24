package com.example.projectmantap11.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.projectmantap11.R;
import com.example.projectmantap11.fragment.BottomSheetFragment;
import com.example.projectmantap11.models.Karyawan;
import com.example.projectmantap11.services.ApiService;
import com.example.projectmantap11.services.RetrofitClientInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private String key = "your auth key here";
    private String uuid = "your uuid key here";
    private String userid = "your userID here";
    private String branchname = "your branch name here";
    private String yourname = "your name here";

    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //context
        contextOfApplication = getApplicationContext();

        //bottom navigation menu
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_about)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        //get needed strings
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        yourname = intent.getStringExtra("name");
        Log.d("FNAME", "onCreate: " + yourname);
        key = intent.getStringExtra("key");
        userid = intent.getStringExtra("employeeid");
        branchname = intent.getStringExtra("corpname");
        Toast.makeText(this, "your key is : "+ key, Toast.LENGTH_SHORT).show();

        //save required data
        SharedPreferences sharedPref = contextOfApplication.getSharedPreferences("CORONA_PROJECT_MANTAP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("uuid", uuid);
        editor.putString("key", key);
        editor.putString("userid", userid);
        editor.putString("yourname", yourname);
        editor.putString("branchname", branchname);
        editor.commit();

        getSupportActionBar().hide();

    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    private void getAllKaryawan() {
        try {
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            Call<List<Karyawan>> call = service
                    .daftarAllKaryawan("Bearer "+key);

            call.enqueue(new Callback<List<Karyawan>>() {

                @Override
                public void onResponse(Call<List<Karyawan>> call, Response<List<Karyawan>> response) {
                    try {
                        for (Karyawan l : response.body()) {
                            Log.d("KARYAWAN", "onResponse: " + l.getName());
                        }
                    } catch (Exception e) {
                        Log.d("ERROR TAG ", "onResponse: " + e);
                    }
                }

                @Override
                public void onFailure(Call<List<Karyawan>> call, Throwable t) {
                    Log.d("GAGAL MANING BOS", "onFailure: ");
                }
            });
        } catch (Exception e) {
            Log.d("API ERROR", "onCreate: " + e);
        }
    }
}