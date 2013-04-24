package com.piplay.commands;

import com.piplay.managers.PiManager;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 5:36 PM
 */
public class NextCommand implements Command {
    @Override
    public void execute() throws Exception {
        PiManager.next();
    }
}
