package com.test.belajardagger2.detailQuestion;

import com.test.belajardagger2.quetions.QuestionWithBody;
import com.test.belajardagger2.questionslist.ObservableViewMvc;

public interface QuestionDetailsViewMVC extends ObservableViewMvc<QuestionDetailsViewMVC.Listener> {
    interface Listener{

    }

    void bindQuestion(QuestionWithBody question);
}
