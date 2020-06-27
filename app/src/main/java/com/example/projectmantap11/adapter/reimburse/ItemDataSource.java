package com.example.projectmantap11.adapter.reimburse;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.projectmantap11.activity.HomeActivity;
import com.example.projectmantap11.models.Reimbursement;
import com.example.projectmantap11.models.ReimbursementServerResponse;
import com.example.projectmantap11.services.ApiService;
import com.example.projectmantap11.services.RetrofitResoClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Reimbursement> {

    Context applicationContext = HomeActivity.getContextOfApplication();

    public static final int PAGE_SIZE = 10;
    private int FIRST_PAGE = 0;
    private int CURRENT_PAGE;
    private int TOTAL_PAGE;

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Reimbursement> callback) {


        SharedPreferences prefs = applicationContext.getSharedPreferences("CORONA_PROJECT_MANTAP", Context.MODE_PRIVATE);
        String key = prefs.getString("key", "empty");
        Log.d("KEYREIMBURSE", "loadInitial: " + key);

        RetrofitResoClientInstance
                .getRetrofit()
                .create(ApiService.class)
                .daftarAllReimbursement("Bearer " + key, 0, FIRST_PAGE)
                .enqueue(new Callback<ReimbursementServerResponse>() {
                    @Override
                    public void onResponse(Call<ReimbursementServerResponse> call, Response<ReimbursementServerResponse> response) {
                        Log.d("REIMBURSERESPONSE", "onResponse: " + response.raw().toString());

                        if (response.body() != null) {

                            callback.onResult(response.body().content, null, FIRST_PAGE + 1);

                            Log.d("REIMBURSERESPONSE", "onResponse: " + response.body().content.toString());
                            TOTAL_PAGE = response.body().total_pages-1;
                            Log.d("REIMBURSETOTALPAGE", "onResponse: "
                                    + response.body().total_pages);
                            CURRENT_PAGE = response.body().number;
                        }
                    }

                    @Override
                    public void onFailure(Call<ReimbursementServerResponse> call, Throwable t) {
                        Log.d("REIMBURSEHISTORYDATA", "onFailure: " + t);

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Reimbursement> callback) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        String key = prefs.getString("key", "empty");

        RetrofitResoClientInstance.getRetrofit().create(ApiService.class).daftarAllReimbursement(key, 0, FIRST_PAGE)
                .enqueue(new Callback<ReimbursementServerResponse>() {
                    @Override
                    public void onResponse(Call<ReimbursementServerResponse> call, Response<ReimbursementServerResponse> response) {

                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        Integer adjacentKey = (params.key > 0) ? params.key - 1 : null;

                        if (response.body() != null) {

                            //passing the loaded data
                            //and the previous page key
                            callback.onResult(response.body().content, adjacentKey);
                            CURRENT_PAGE = response.body().number;

                        }
                    }

                    @Override
                    public void onFailure(Call<ReimbursementServerResponse> call, Throwable t) {
                        Log.d("REIMBURSEHISTORYDATA", "onFailure: " + t);

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Reimbursement> callback) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        String key = prefs.getString("key", "empty");

        RetrofitResoClientInstance.getRetrofit().create(ApiService.class).daftarAllReimbursement(key, 0, FIRST_PAGE)
                .enqueue(new Callback<ReimbursementServerResponse>() {
                    @Override
                    public void onResponse(Call<ReimbursementServerResponse> call, Response<ReimbursementServerResponse> response) {


                        if (response.body() != null) {

                            Integer key;
                            //if the response has next page
                            //incrementing the next page number
                            if (CURRENT_PAGE != TOTAL_PAGE) {
                                key = params.key + 1;
                            } else {
                                key = null;
                            }

                            callback.onResult(response.body().content, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReimbursementServerResponse> call, Throwable t) {
                        Log.d("REIMBURSEHISTORYDATA", "onFailure: " + t);

                    }
                });
    }
}