package com.piplay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.actionbarsherlock.app.SherlockListFragment;
import com.piplay.R;
import com.piplay.adapters.SongsAdapter;
import com.piplay.pojos.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:39 AM
 */
public class SongsFragment extends SherlockListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Song> songs = new ArrayList<Song>();
        songs.add(new Song("abc"));
        ArrayAdapter<Song> adapter = new SongsAdapter(getSherlockActivity(), 0,
                songs);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
