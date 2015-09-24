package com.freeman.example.apitest.appcompatdialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

/**
 * Created by freeman on 9/22/15.
 */
public class DialogAppCompatTest extends AppCompatDialog {

    public DialogAppCompatTest(Context context) {
        super(context);
    }

    public DialogAppCompatTest(Context context, int theme) {
        super(context, theme);
    }

    protected DialogAppCompatTest(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
