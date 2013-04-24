package com.piplay.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 12:06 PM
 */
public class SongsResponse {
    @SerializedName("ResultCount")
    private int count;
    @SerializedName("Data")
    private List<Song> songs;

    public int getCount() {
        return count;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
