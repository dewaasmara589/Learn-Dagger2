package com.test.belajardagger2.dependencyInjection;

import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentManager;

import com.test.belajardagger2.common.Constants;
import com.test.belajardagger2.common.DialogsManager;
import com.test.belajardagger2.common.DialogsManagerFactory;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.quetions.FetchQuestionDetailsUseCase;
import com.test.belajardagger2.quetions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@UiThread
public class CompositionRoot {
    private Retrofit retrofit;
    private StackoverflowApi stackoverflowApi;

    @UiThread
    private Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    @UiThread
    private StackoverflowApi getStackoverflowApi(){
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

    public DialogsManagerFactory getDialogsManagerFactory(){
        return new DialogsManagerFactory();
    }
}
