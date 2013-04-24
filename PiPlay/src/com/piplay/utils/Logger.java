package com.piplay.utils;

import android.util.Log;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 11:56 AM
 */
public class Logger {

    private static final String TAG = "PiPlay";
    private static final boolean DEBUG = true;

    public static void v(String message) {
        if (DEBUG) {
            Log.v(TAG, message);
        }
    }
}
