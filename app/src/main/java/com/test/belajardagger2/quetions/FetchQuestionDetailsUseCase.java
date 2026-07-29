package com.test.belajardagger2.quetions;

import androidx.annotation.Nullable;

import com.test.belajardagger2.networking.SingleQuestionResponseSchema;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.questionslist.BaseObservable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {
    public interface Listener{
        void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();
    }

    private final StackoverflowApi mStackoverflowApi;

    @Nullable
    Call<SingleQuestionResponseSchema> call;

    public FetchQuestionDetailsUseCase(StackoverflowApi stackoverflowApi){

        mStackoverflowApi = stackoverflowApi;
    }

    public void fetchQuestionDetailsAndNotify(String questionId){
        cancelCurrentFetchIfActive();

        call = mStackoverflowApi.questionDetails(questionId);
        call.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()){
                    notifySucceedded(response.body().getQuestions());
                }else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable throwable) {
                if (call != null && !call.isCanceled() && !call.isExecuted()){
                    call.cancel();
                }
            }
        });
    }

    private void cancelCurrentFetchIfActive(){
        if (call != null && !call.isCanceled() && !call.isExecuted()){
            call.cancel();
        }
    }

    private void notifySucceedded(QuestionWithBody question){
        for (Listener listener : getListeners()){
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed(){
        for (Listener listener : getListeners()){
            listener.onFetchOfQuestionDetailsFailed();
        }
    }

}
