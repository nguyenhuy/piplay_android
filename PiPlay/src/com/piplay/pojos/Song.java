package com.piplay.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 10:48 AM
 */
public class Song implements Serializable {
    @SerializedName("Title")
    private String title;
    @SerializedName("LinkDownload128")
    private String link;
    @SerializedName("Artist")
    private String artist;
    @SerializedName("ID")
    private String id;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
    public String getArtist(){
        return artist;
    }

    public String getId() {
        return id;
    }
}
