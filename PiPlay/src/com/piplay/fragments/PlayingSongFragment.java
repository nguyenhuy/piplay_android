package com.piplay.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.piplay.R;
import com.piplay.commands.Command;
import com.piplay.pojos.Song;
import com.piplay.tasks.TaskListener;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 4:44 PM
 */
public class PlayingSongFragment extends SherlockFragment
        implements View.OnClickListener {
    public static final String EXTRA_SONG = "song";

    public interface Listener {
        void hideProgress();

        void playSong(Song song, TaskListener listener);

        void pauseSong(TaskListener listener);

        void nextSong(TaskListener listener);

        void previousSong(TaskListener listener);
    }

    public static PlayingSongFragment newInstance(Song song) {
        PlayingSongFragment fragment = new PlayingSongFragment();

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SONG, song);
        fragment.setArguments(args);

        return fragment;
    }

    private Listener mListener;
    private boolean mIsPlaying;
    private ImageButton mPlayBtn;
    private Song mSong;

    private TaskListener mCommandTaskListener = new TaskListener() {
        @Override
        public void onFinished(AsyncTask task, Object result) {
            if (mListener != null) {
                mListener.hideProgress();
            }
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSong = (Song) getArguments().getSerializable(EXTRA_SONG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_playing_song,
                container,
                false);
        mPlayBtn = (ImageButton) v.findViewById(R.id.ib_play);
        mPlayBtn.setOnClickListener(this);
        v.findViewById(R.id.ib_previous).setOnClickListener(this);
        v.findViewById(R.id.ib_next).setOnClickListener(this);

        mIsPlaying = false;
        if (mListener != null) {
            mListener.playSong(mSong, mCommandTaskListener);
        }

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_next:
                if (mListener != null) {
                    mListener.nextSong(mCommandTaskListener);
                }
                break;
            case R.id.ib_previous:
                if (mListener != null) {
                    mListener.previousSong(mCommandTaskListener);
                }
                break;
            case R.id.ib_play:
                if (mListener != null) {
                    togglePlayer();
                }
                break;
        }
    }

    private void togglePlayer() {
        if (mListener != null) {
            mListener.pauseSong(mCommandTaskListener);
        }
        mIsPlaying = !mIsPlaying;
        if (mIsPlaying) {
            mPlayBtn.setImageResource(R.drawable.button_pause);
        } else {
            mPlayBtn.setImageResource(R.drawable.button_play);
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
