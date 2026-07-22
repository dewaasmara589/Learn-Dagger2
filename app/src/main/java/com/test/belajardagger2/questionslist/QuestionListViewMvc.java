package com.test.belajardagger2.questionslist;

import com.test.belajardagger2.Question;

import java.util.List;
import java.util.Observable;

public interface QuestionListViewMvc extends ObservableViewMvc<QuestionListViewMvc.Listener> {
    interface Listener {
        void onQuestionClicked(Question question);
    }
    void bindQuestions(List<Question> questions);
}
