package com.piplay.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.piplay.R;
import com.piplay.adapters.SongsAdapter;
import com.piplay.pojos.Song;
import com.piplay.pojos.SongsResponse;
import com.piplay.tasks.TaskListener;

import java.util.ArrayList;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:39 AM
 */
public class SongsFragment extends SherlockListFragment {

    public interface Listener {
        void hideProgress();

        void searchSong(TaskListener listener, String keyword);

        void showSong(Song song);
    }

    private EditText mKeywordEt;
    private Listener mListener;

    private View.OnClickListener mSearchBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.searchSong(
                        mSearchSongsTaskListener,
                        mKeywordEt.getText().toString());
            }
        }
    };

    private TaskListener mSearchSongsTaskListener = new TaskListener() {

        @Override
        public void onFinished(AsyncTask task, Object result) {
            SongsResponse response = (SongsResponse) result;
            if (mListener != null) {
                mListener.hideProgress();
            }
            if (response.getCount() == 0 || response.getSongs().size() == 0) {
                return;
            }

            SongsAdapter adapter = (SongsAdapter) getListAdapter();
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.addAll(response.getSongs());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onException(AsyncTask task, Exception exc) {
            handleException(exc);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (Listener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_search)
                .setOnClickListener(mSearchBtnClickListener);
        mKeywordEt = (EditText) view.findViewById(R.id.et_keywork);

        ArrayAdapter<Song> adapter = new SongsAdapter(
                getSherlockActivity(),
                0,
                new ArrayList<Song>());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Song song = (Song) getListAdapter().getItem(position);
        if (mListener != null) {
            mListener.showSong(song);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void handleException(Exception exc) {
        Toast.makeText(
                getSherlockActivity(),
                exc.getLocalizedMessage(),
                Toast.LENGTH_LONG)
                .show();
        if (mListener != null) {
            mListener.hideProgress();
        }
    }
}
