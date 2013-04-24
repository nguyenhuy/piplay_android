package com.piplay.commands;

import com.piplay.managers.PiManager;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 4:11 PM
 */
public class AddSongCommand implements Command {
    private String mUrl;

    public AddSongCommand(String url) {
        this.mUrl = url;
    }

    @Override
    public void execute() throws Exception {
        PiManager.addSong(mUrl);
    }
}
