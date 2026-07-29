package com.test.belajardagger2;

import android.app.Application;

import androidx.annotation.UiThread;

import com.google.gson.Gson;
import com.test.belajardagger2.common.Constants;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.quetions.FetchQuestionDetailsUseCase;
import com.test.belajardagger2.quetions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private Retrofit retrofit;
    private StackoverflowApi stackoverflowApi;

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

    @UiThread
    public StackoverflowApi getStackoverflowApi(){
        if (stackoverflowApi == null){
            stackoverflowApi = getRetrofit().create(StackoverflowApi.class);
        }
        return stackoverflowApi;
    }

    @UiThread
    public FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase(){
        return new FetchQuestionDetailsUseCase(getStackoverflowApi());
    }

    @UiThread
    public FetchQuestionsListUseCase fetchQuestionsListUseCase(){
        return new FetchQuestionsListUseCase(getStackoverflowApi());
    }
}
