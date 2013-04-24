package com.piplay.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:48 AM
 */
public class Song {
    @SerializedName("Title")
    private String title;
    @SerializedName("LinkDownload128")
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
