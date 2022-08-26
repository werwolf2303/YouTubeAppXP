package com.player;

public class Utils {
    public String getId(String url) {
        if(url.contains("&")) {
            url = url.split("&")[0];
        }
        try {
            return url.split("\\?v=")[1];
        }catch (ArrayIndexOutOfBoundsException ex) {
            return "INVALID";
        }
    }
}
