package com.test.belajardagger2.networking;

import com.google.gson.annotations.SerializedName;
import com.test.belajardagger2.quetions.QuestionWithBody;

import java.util.Collections;
import java.util.List;

public class SingleQuestionResponseSchema {
    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;


    public SingleQuestionResponseSchema(QuestionWithBody questions) {
        mQuestions = Collections.singletonList(questions);
    }

    public QuestionWithBody getQuestions() {
        return mQuestions.get(0);
    }
}
