package com.test.belajardagger2.common;

import androidx.appcompat.app.AppCompatActivity;

import com.test.belajardagger2.MyApplication;
import com.test.belajardagger2.dependencyInjection.CompositionRoot;

public class BaseActivity extends AppCompatActivity {
    protected CompositionRoot getCompositionRoot(){
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
