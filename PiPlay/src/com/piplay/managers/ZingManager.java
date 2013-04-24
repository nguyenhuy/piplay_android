package com.piplay.managers;

import android.text.TextUtils;
import android.util.Base64;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.piplay.pojos.SongsResponse;
import com.piplay.utils.Logger;
import com.piplay.utils.NetworkUtil;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Huy Nguyen
 * Date: 4/24/13
 * Time: 11:39 AM
 */
public class ZingManager {

    public static final String BASE_SEARCH_URL = "http://api.mp3.zing" +
            ".vn/api/search?jsondata=";
    public static final String PUB = "0470d704c5a055e1a0fecf65be0ca5c2adab9952";
    public static final String PV_KEY = "5fe5208a5d9c982a5d8d869a0873ac08";

    private static String getSearchSongResource(String kw, int page) {

        Map map = new HashMap();
        map.put("t", "0");       //Search by song(any)
        map.put("kw", kw);
        map.put("rc", 100);
        map.put("p", page);

        Gson gson = new Gson();
        String json = gson.toJson(map);
        Logger.v("Base64 "
                + new String(Base64.encode(json.getBytes(), Base64.DEFAULT)));
        String data = URLEncoder.encode(
                new String(Base64.encode(json.getBytes(), Base64.DEFAULT)))
                .replace("%0A", "");
        Logger.v("urlencode " + data);
        String signature = "";
        try {
            signature = computeSignature(data, PV_KEY);
        } catch (UnsupportedEncodingException e) {
        } catch (GeneralSecurityException e) {
        }

        Logger.v("signature " + signature);

        return data + "&publicKey=" + PUB + "&signature=" + signature;
    }

    private static String computeSignature(String baseString, String keyString)
            throws GeneralSecurityException, UnsupportedEncodingException {

        SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(),
                "HmacMD5");

        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(keySpec);
        byte[] rawHmac = mac.doFinal(baseString.getBytes());

        return new String(Hex.encodeHex(rawHmac));
    }

    private static void validateResponse(HttpRequest request) throws Exception {
        if (!request.ok()) {
            throw new Exception("Request failed with code: " + request.code());
        }
    }

    public static SongsResponse searchSongs(String keyword, int page)
            throws Exception {
        if (TextUtils.isEmpty(keyword)) {
            throw new Exception("Keyword is empty.");
        }

        String url = BASE_SEARCH_URL + getSearchSongResource(keyword, page);
        HttpRequest request = HttpRequest.get(url);
        validateResponse(request);
        String responseString = request.body();
        Logger.v(responseString);
        return new Gson().fromJson(responseString, SongsResponse.class);
    }
}
