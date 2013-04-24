package com.piplay.tasks;

import android.os.AsyncTask;
import com.piplay.managers.ZingManager;
import com.piplay.pojos.SongsResponse;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 12:18 PM
 */
public class SearchSongsTask extends AsyncTask<Void, Void, SongsResponse> {

    public interface Listener {
        void onFinished(SongsResponse response);

        void onException(Exception exc);
    }

    private Listener mListener;
    private String mKeyword;
    private int mPage;
    private Exception mException;

    public SearchSongsTask(Listener listener, String keyword, int page) {
        this.mListener = listener;
        this.mKeyword = keyword;
        this.mPage = page;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    @Override
    protected SongsResponse doInBackground(Void... voids) {
        try {
            return ZingManager.searchSongs(mKeyword, mPage);
        } catch (Exception e) {
            mException = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(SongsResponse songsResponse) {
        super.onPostExecute(songsResponse);
        if (isCancelled()) {
            return;
        }

        if (mException != null) {
            if (mListener != null) {
                mListener.onException(mException);
            }
        } else if (songsResponse != null) {
            if (mListener != null) {
                mListener.onFinished(songsResponse);
            }
        }
    }
}
