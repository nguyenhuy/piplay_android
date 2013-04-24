package com.piplay.tasks;

import android.os.AsyncTask;
import com.piplay.commands.Command;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 4:00 PM
 */
public class CommandTask extends AsyncTask<Command, Void, Exception> {

    private TaskListener mListener;
    private Command mCommand;

    public CommandTask(TaskListener mListener) {
        this.mListener = mListener;
    }

    public void setListener(TaskListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected Exception doInBackground(Command... commands) {
        mCommand = commands[0];
        if (mCommand != null) {
            try {
                mCommand.execute();
            } catch (Exception e) {
                return e;
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Exception e) {
        super.onPostExecute(e);
        if (isCancelled()) {
            return;
        }
        if (mListener == null) {
            return;
        }

        if (e != null) {
            mListener.onException(this, e);
        } else {
            mListener.onFinished(this, mCommand);
        }
    }
}
