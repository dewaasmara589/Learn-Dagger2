package com.test.belajardagger2;

import android.app.Application;

import androidx.annotation.UiThread;

import com.google.gson.Gson;
import com.test.belajardagger2.common.Constants;
import com.test.belajardagger2.dependencyInjection.CompositionRoot;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.quetions.FetchQuestionDetailsUseCase;
import com.test.belajardagger2.quetions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    private CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();

        compositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot(){
        return compositionRoot;
    }
}
