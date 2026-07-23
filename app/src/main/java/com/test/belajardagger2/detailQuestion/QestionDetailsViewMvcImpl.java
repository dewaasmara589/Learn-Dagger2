package com.test.belajardagger2.detailQuestion;

import android.os.Build;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.belajardagger2.quetions.QuestionWithBody;
import com.test.belajardagger2.R;
import com.test.belajardagger2.questionslist.BaseViewMVC;

public class QestionDetailsViewMvcImpl extends BaseViewMVC<QuestionDetailsViewMVC.Listener>
        implements QuestionDetailsViewMVC {

    private final TextView mTxtQuestionBody;

    public QestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup container){
        setRootView(inflater.inflate(R.layout.activity_question_details, container, false));
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
        mTxtQuestionBody.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void bindQuestion(QuestionWithBody question) {
        String questionBody = question.getmBody();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        }else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
    }
}
