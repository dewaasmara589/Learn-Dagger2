package com.test.belajardagger2.common;

import android.view.LayoutInflater;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.test.belajardagger2.MyApplication;
import com.test.belajardagger2.dependencyInjection.CompositionRoot;
import com.test.belajardagger2.dependencyInjection.PresentationCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private PresentationCompositionRoot presentationCompositionRoot;

    @UiThread
    protected PresentationCompositionRoot getCompositionRoot(){
        if (presentationCompositionRoot == null){
            presentationCompositionRoot = new PresentationCompositionRoot(
                    getAppCompositionRoot(),
                    getSupportFragmentManager(),
                    LayoutInflater.from(this)
            );
        }
        return presentationCompositionRoot;
    }

    protected CompositionRoot getAppCompositionRoot(){
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
