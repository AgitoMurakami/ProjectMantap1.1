package com.example.projectmantap11.services;

import com.example.projectmantap11.models.Authorization;
import com.example.projectmantap11.models.Karyawan;
import com.example.projectmantap11.models.Reimbursement;
import com.example.projectmantap11.models.ReimbursementServerResponse;

import java.util.Collection;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    // dummy url
    //String URL = "https://auth-servercorona.cfapps.io/oauth/token/";

    /*
    URL/api/daftar-karyawan
    GET : list karyawan
    using auth token as bearer token
     */

    @Multipart
    @POST("oauth/token")
    Call<Authorization> loginAccount(@Header("Authorization") String authkey,
                                     @Part("grant_type") RequestBody granttype,
                                     @Part("username") RequestBody username,
                                     @Part("password") RequestBody password);

    /*
    @GET("accounts/{accountId}")
    Call<AccountInfo> getAccountInfo(@Header("Authorization") String authKey,
                                     @Path("accountId") String accountId);

     */

    @GET("api/daftar-karyawan")
    Call<List<Karyawan>> daftarAllKaryawan(@Header("Authorization") String authkey);

    @GET("/api/reimburse")
    Call<ReimbursementServerResponse> daftarAllReimbursement(@Header("Authorization") String authkey,
                                                             @Query("progress") int progress,
                                                             @Query("page") int page);

//    @PUT("/ubah-password")
//    Call<>gantipassword(@Header("Authorization") String authkey);
}
