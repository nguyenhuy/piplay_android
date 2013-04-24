package com.piplay.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.piplay.R;
import com.piplay.fragments.ProgressDialogFragment;
import com.piplay.fragments.SongsFragment;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:32 AM
 */
public class MainActivity extends SherlockFragmentActivity
        implements SongsFragment.Listener {

    private static final String TAG_SONGS_FRAGMENT = "songs_fragment";
    private static final String TAG_PROGRESS_DIALOG_FRAGMENT =
            "progress_dialog_fragment";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment songsFragment = new SongsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, songsFragment, TAG_SONGS_FRAGMENT)
                .commit();
    }

    @Override
    public void showProgress() {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.show(getSupportFragmentManager().beginTransaction(),
                TAG_PROGRESS_DIALOG_FRAGMENT);
    }

    @Override
    public void hideProgress() {
        ProgressDialogFragment fragment =
                (ProgressDialogFragment)getSupportFragmentManager()
                        .findFragmentByTag(TAG_PROGRESS_DIALOG_FRAGMENT);
        if (fragment != null) {
            fragment.dismiss();
        }
    }
}