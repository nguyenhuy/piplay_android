package com.piplay.tasks;

import android.os.AsyncTask;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 4:09 PM
 */
public interface TaskListener {
    void onFinished(AsyncTask task, Object result);

    void onException(AsyncTask task, Exception exc);
}
