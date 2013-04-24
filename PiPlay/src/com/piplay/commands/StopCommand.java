package com.piplay.commands;

import com.piplay.managers.PiManager;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 4:34 PM
 */
public class StopCommand implements Command {

    @Override
    public void execute() throws Exception {
        PiManager.stop();
    }
}
