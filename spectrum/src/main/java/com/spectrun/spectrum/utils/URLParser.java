package com.spectrun.spectrum.utils;

import java.net.URL;

public class URLParser {
    public static String getPortFromAddress(String url) throws Exception {
        String port;
        try {
            URL parsedUrl = new URL(url);
            port = String.valueOf(parsedUrl.getPort());
            return port;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }
}
