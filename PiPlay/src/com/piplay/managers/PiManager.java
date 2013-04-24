package com.piplay.managers;

import android.text.TextUtils;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 3:21 PM
 */
public class PiManager {
    private static final String BASE_URL =
            "http://192.168.1.90:8080/requests/status.xml";

    private static final String PARAM_COMMAND = "command";
    private static final String PARAM_INPUT = "input";

    private static final String COMMAND_STOP = "pl_stop";
    private static final String COMMAND_PLAY = "in_play";

    private static void validateResponse(HttpRequest request) throws Exception {
        if (!request.ok()) {
            throw new Exception("Request failed with code: " + request.code());
        }
    }

    public static void addSong(String url) throws Exception {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        HttpRequest request = HttpRequest.get(BASE_URL, true,
                PARAM_COMMAND, COMMAND_PLAY,
                PARAM_INPUT, url);
        validateResponse(request);
        String response = request.body();
    }
}
