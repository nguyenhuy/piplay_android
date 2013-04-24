package com.piplay.commands;

import com.piplay.managers.PiManager;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 5:37 PM
 */
public class PreviousCommand implements Command {
    @Override
    public void execute() throws Exception {
        PiManager.previous();
    }
}
