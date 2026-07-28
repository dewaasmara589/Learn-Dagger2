package com.test.belajardagger2;

import android.app.Application;

import androidx.annotation.UiThread;

import com.google.gson.Gson;
import com.test.belajardagger2.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private Retrofit retrofit;

    @UiThread
    public Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
