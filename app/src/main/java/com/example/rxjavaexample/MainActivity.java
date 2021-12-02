package com.example.rxjavaexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        String cityName = "Seoul";
        String apiKey = "1b9d3024cb88207299e87d357b8c7ee1";
        String units = "metric";
        String language = "kr";
        Call<GetResult> call = service.getResult(cityName, apiKey, units, language);

        call.enqueue(new Callback<GetResult>() {
            @Override
            public void onResponse(Call<GetResult> call, Response<GetResult> response) {
                if (response.isSuccessful()) {
                    GetResult result = response.body();
                    Log.d(TAG, "onResponse: 성공, 결과\n" + result.toString());
                } else {
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<GetResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}