package com.piplay.commands;

import com.piplay.managers.PiManager;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 4:38 PM
 */
public class PauseCommand implements Command {
    @Override
    public void execute() throws Exception {
        PiManager.pause();
    }
}
