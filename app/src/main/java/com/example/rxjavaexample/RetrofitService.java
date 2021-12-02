package com.example.rxjavaexample;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("data/2.5/weather")
    Call<GetResult> getResult(@Query("q") String q, @Query("appid") String appid,
                              @Query("units") String units, @Query("lang") String lang);
}
