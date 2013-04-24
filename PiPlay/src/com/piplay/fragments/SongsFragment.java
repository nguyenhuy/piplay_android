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
import com.piplay.commands.AddSongCommand;
import com.piplay.commands.Command;
import com.piplay.pojos.Song;
import com.piplay.pojos.SongsResponse;
import com.piplay.tasks.CommandTask;
import com.piplay.tasks.SearchSongsTask;
import com.piplay.tasks.TaskListener;

import java.util.ArrayList;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:39 AM
 */
public class SongsFragment extends SherlockListFragment {

    public interface Listener {
        void showProgress();

        void hideProgress();
    }

    private EditText mKeywordEt;
    private AsyncTask<?, ?, ?> mCurrentTask;
    private Listener mListener;

    private View.OnClickListener mSearchBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startTask(
                    new SearchSongsTask(
                            mSearchSongsTaskListener,
                            mKeywordEt.getText().toString(),
                            0).execute());
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

    private TaskListener mCommandTaskListener = new TaskListener() {
        @Override
        public void onFinished(AsyncTask task, Object result) {
            if (mListener != null) {
                mListener.hideProgress();
            }
            Command command = (Command) result;
            // Nothing to do now
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
        Command command = new AddSongCommand(song.getLink());
        startTask(new CommandTask(mCommandTaskListener).execute(command));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void startTask(AsyncTask task) {
        stopTask();
        mCurrentTask = task;
        if (mListener != null) {
            mListener.showProgress();
        }
    }

    private void stopTask() {
        if (mCurrentTask != null && mCurrentTask.getStatus() != AsyncTask.Status.FINISHED) {
            mCurrentTask.cancel(true);

            if (mCurrentTask instanceof SearchSongsTask) {
                ((SearchSongsTask) mCurrentTask).setListener(null);
            } else if (mCurrentTask instanceof CommandTask) {
                ((CommandTask) mCurrentTask).setListener(null);
            }

            mCurrentTask = null;
        }
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
