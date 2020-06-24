package com.example.projectmantap11.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitResoClientInstance {

    private static final String BASE_RESO_URL = "https://resource-requestcorona.cfapps.io/";

    private static Retrofit retrofit;


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_RESO_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
