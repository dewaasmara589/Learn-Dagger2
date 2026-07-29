package com.test.belajardagger2.dependencyInjection;

import android.view.LayoutInflater;

import androidx.fragment.app.FragmentManager;

import com.test.belajardagger2.common.DialogsManager;
import com.test.belajardagger2.common.ViewMvcFactory;
import com.test.belajardagger2.questionslist.ViewMvc;
import com.test.belajardagger2.quetions.FetchQuestionDetailsUseCase;
import com.test.belajardagger2.quetions.FetchQuestionsListUseCase;

public class PresentationCompositionRoot {

    private final CompositionRoot compositionRoot;
    private final FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;


    public PresentationCompositionRoot(CompositionRoot compositionRoot,
                                       FragmentManager fragmentManager,
                                       LayoutInflater layoutInflater) {
        this.compositionRoot = compositionRoot;
        this.fragmentManager = fragmentManager;
        this.layoutInflater = layoutInflater;
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(fragmentManager);
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase(){
        return compositionRoot.fetchQuestionDetailsUseCase();
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase(){
        return compositionRoot.fetchQuestionsListUseCase();
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(layoutInflater);
    }
}
