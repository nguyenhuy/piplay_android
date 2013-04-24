package com.piplay.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.piplay.R;
import com.piplay.commands.AddSongCommand;
import com.piplay.commands.Command;
import com.piplay.fragments.PlayingSongFragment;
import com.piplay.fragments.ProgressDialogFragment;
import com.piplay.fragments.SongsFragment;
import com.piplay.pojos.Song;
import com.piplay.tasks.CommandTask;
import com.piplay.tasks.SearchSongsTask;
import com.piplay.tasks.TaskListener;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:32 AM
 */
public class MainActivity extends SherlockFragmentActivity
        implements SongsFragment.Listener, PlayingSongFragment.Listener {

    private static final String TAG_SONGS_FRAGMENT = "songs_fragment";
    private static final String TAG_PROGRESS_DIALOG_FRAGMENT =
            "progress_dialog_fragment";
    private static final String TAG_PLAYING_SONG_FRAGMENT =
            "playing_song_fragment";

    private AsyncTask<?, ?, ?> mCurrentTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment songsFragment = new SongsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, songsFragment, TAG_SONGS_FRAGMENT)
                .commit();
    }

    public void showProgress() {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.show(getSupportFragmentManager().beginTransaction(),
                TAG_PROGRESS_DIALOG_FRAGMENT);
    }

    @Override
    public void hideProgress() {
        ProgressDialogFragment fragment =
                (ProgressDialogFragment) getSupportFragmentManager()
                        .findFragmentByTag(TAG_PROGRESS_DIALOG_FRAGMENT);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void searchSong(TaskListener listener, String keyword) {
        startTask(new SearchSongsTask(listener, keyword, 0).execute());
    }

    @Override
    public void showSong(Song song) {
        PlayingSongFragment fragment = PlayingSongFragment.newInstance(song);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, TAG_PLAYING_SONG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void playSong(Song song, TaskListener listener) {
        Command command = new AddSongCommand(song.getLink());
        startTask(new CommandTask(listener).execute(command));
    }

    @Override
    public void nextSong() {
    }

    @Override
    public void previousSong() {
    }

    private void startTask(AsyncTask task) {
        stopTask();
        mCurrentTask = task;
        showProgress();
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
}