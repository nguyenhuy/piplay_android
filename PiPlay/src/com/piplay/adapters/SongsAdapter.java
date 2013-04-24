package com.piplay.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.piplay.R;
import com.piplay.pojos.Song;

import java.util.List;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:48 AM
 */
public class SongsAdapter extends ArrayAdapter<Song> {

    public SongsAdapter(Context context, int textViewResourceId, List<Song> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_song, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Song song = getItem(position);
        viewHolder.title.setText(song.toString());

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
    }
}
