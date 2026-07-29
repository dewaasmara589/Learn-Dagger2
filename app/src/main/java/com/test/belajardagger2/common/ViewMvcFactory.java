package com.test.belajardagger2.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.test.belajardagger2.detailQuestion.QuestionDetailsViewMVC;
import com.test.belajardagger2.detailQuestion.QuestionDetailsViewMvcImpl;
import com.test.belajardagger2.questionslist.QuestionListViewMVCImpl;
import com.test.belajardagger2.questionslist.QuestionListViewMvc;
import com.test.belajardagger2.questionslist.ViewMvc;

public class ViewMvcFactory {
    private final LayoutInflater layoutInflater;


    public ViewMvcFactory(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    // New Instance
    public <T extends ViewMvc> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container){
        ViewMvc viewMvc;

        if (mvcViewClass == QuestionListViewMvc.class){
            viewMvc = new QuestionListViewMVCImpl(layoutInflater, container);
        }else if (mvcViewClass == QuestionDetailsViewMVC.class){
            viewMvc = new QuestionDetailsViewMvcImpl(layoutInflater, container);
        }else{
            throw new IllegalArgumentException("unsupported MVC view class " + mvcViewClass);
        }

        return (T) viewMvc;
    }
}
