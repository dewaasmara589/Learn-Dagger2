package com.test.belajardagger2.quetions;

import androidx.annotation.Nullable;

import com.test.belajardagger2.common.Constants;
import com.test.belajardagger2.networking.QuestionsListResponseSchema;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.questionslist.BaseObservable;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionsListUseCase extends BaseObservable<FetchQuestionsListUseCase.Listener> {
    public interface Listener{
        void onFetchQuestionsSucceeded(List<Question> questions);
        void onFetchQuestionsFailed();
    }

    private final StackoverflowApi stackoverflowApi;

    @Nullable
    Call<QuestionsListResponseSchema> call;

    public FetchQuestionsListUseCase(Retrofit retrofit){

        this.stackoverflowApi = retrofit.create(StackoverflowApi.class);
    }

    public void fetchLastActiveQuestionsAndNotify(int numOfQuestions){
        cancelCurrentFetchIfActive();

        call = stackoverflowApi.lastActiveQuestions(numOfQuestions);
        call.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful()){
                    notifySucceeded(response.body().getQuestions());
                }else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<QuestionsListResponseSchema> call, Throwable throwable) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive(){
        if (call != null && !call.isCanceled() && !call.isExecuted()){
            call.cancel();
        }
    }

    private void notifySucceeded(List<Question> questions){
        List<Question> unmodifiableQuestions = Collections.unmodifiableList(questions);
        for (Listener listener : getListeners()){
            listener.onFetchQuestionsSucceeded(unmodifiableQuestions);
        }
    }

    private void notifyFailed(){
        for (Listener listener : getListeners()){
            listener.onFetchQuestionsFailed();
        }
    }
}
