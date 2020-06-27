package com.example.projectmantap11.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitResoClient2Instance {

    private static final String BASE_URL = "https://resource-requestcorona.cfapps.io/";
    private static RetrofitResoClient2Instance mInstance;
    private Retrofit retrofit;


    private RetrofitResoClient2Instance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitResoClient2Instance getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitResoClient2Instance();
        }
        return mInstance;
    }

    public ApiService getApi() {
        return retrofit.create(ApiService.class);
    }
}