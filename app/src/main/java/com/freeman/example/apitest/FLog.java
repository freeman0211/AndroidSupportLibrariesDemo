package com.freeman.example.apitest;

import android.util.Log;

/**
 * Created by freeman on 9/19/15.
 */
public class FLog {

    private static final boolean DEBUG = true;

    public static void d (String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i (String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

}
