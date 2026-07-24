package com.test.belajardagger2.common;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@UiThread
public class DialogsManager {

    public static final String ARGUMENT_DIALOG_ID = "ARGUMENT_DIALOG_ID";
    public static final String DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG";

    private FragmentManager mFragmentManager;
    private DialogFragment mCurrentlyShownDialog;

    public DialogsManager(FragmentManager mFragmentManager){
        this.mFragmentManager = mFragmentManager;

        Fragment fragmentwithDialogTag= mFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG);

        if (fragmentwithDialogTag != null && DialogFragment.class.isAssignableFrom(
                fragmentwithDialogTag.getClass())){
            mCurrentlyShownDialog = (DialogFragment) fragmentwithDialogTag;
        }
    }

    @Nullable
    public DialogFragment getmCurrentlyShownDialog(){
        return mCurrentlyShownDialog;
    }

    public String getCurrentlyShownDialogId(){
        if (mCurrentlyShownDialog == null || mCurrentlyShownDialog.getArguments() == null ||
                !mCurrentlyShownDialog.getArguments().containsKey(ARGUMENT_DIALOG_ID)) {
            return "";
        } else {
            return mCurrentlyShownDialog.getArguments().getString(ARGUMENT_DIALOG_ID);
        }
    }

    public boolean isDialogCurrentlyShown(String id){
        String shownDialogId = getCurrentlyShownDialogId();
        return !TextUtils.isEmpty(shownDialogId) && shownDialogId.equals(id);
    }

    public void dismissCurrentlyShownDialog(){
        if (mCurrentlyShownDialog != null){
            mCurrentlyShownDialog.dismissAllowingStateLoss();
            mCurrentlyShownDialog = null;
        }
    }

    public void shownRetainedDialogWithId(DialogFragment dialog, @Nullable String id){
        dismissCurrentlyShownDialog();
        dialog.setReenterTransition(true);
        setId(dialog, id);
        showDialog(dialog);
    }

    private void setId(DialogFragment dialog, String id){
        Bundle args = dialog.getArguments() != null ? dialog.getArguments() : new Bundle(1);
        args.putString(ARGUMENT_DIALOG_ID, id);
        dialog.setArguments(args);
    }

    private void showDialog(DialogFragment dialog){
        mFragmentManager.beginTransaction().add(dialog, DIALOG_FRAGMENT_TAG).commitAllowingStateLoss();
        mCurrentlyShownDialog = dialog;
    }
}
