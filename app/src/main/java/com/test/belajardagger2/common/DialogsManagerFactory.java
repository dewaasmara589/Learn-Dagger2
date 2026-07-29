package com.test.belajardagger2.common;

import androidx.fragment.app.FragmentManager;

public class DialogsManagerFactory {
    public DialogsManager newDialogsManager(FragmentManager fragmentManager){
        return new DialogsManager(fragmentManager);
    }
}
