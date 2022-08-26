package com.player;

import com.libdialog.lib.libWget;

import java.util.ArrayList;

public class YouTubeAPI {
    public static String apikey = new ConfigHandler().read("APIKey");
    libWget wget = new libWget();
    public YouTubeAPI() {
        if(apikey.equals("YOUR_OWN_API_KEY__OPTIONAL")) {
            apikey = "AIzaSyAJJtv7UV54PTVFBtLxWTnMOLebY5qGuGw";
        }
    }
    public String[] search(String tosearch, boolean showdescription) {
        String res = wget.get("https://www.googleapis.com/youtube/v3/search?key=" + apikey + "&q=" + tosearch.replace(" ", "%20"));
        String conv1 = res.split("items")[1];
        String[] conv2 = conv1.split("\n");
        ArrayList<String> ids = new ArrayList<String>();
        for(String s : conv2) {
            if(s.contains("videoId")) {
                ids.add(s.replace("\"videoId\": \"", "").replace("\"", "").replace("        ", ""));
            }
        }
        String[] id = ids.toArray(new String[0]);
        ArrayList<String> comp = new ArrayList<String>();
        for(String s : id) {
            String[] conv = wget.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + s + "&key=" + apikey).split("\n");
            String title = "";
            String description = "";
            for(String c : conv) {
                if (c.contains("title")) {
                    title = c.replace("\"title\"", "").replace("\"", "").replace(",", "").replace("          : ", "");
                }
                if(c.contains("description")) {
                    description = c.replace("\"description\"", "").replace("\"", "").replace("          : ", "");
                }
            }
            if(showdescription) {
                comp.add(title + ":" + description + ":" + s);
            }else{
                comp.add(title + ":" + s);
            }
        }
        return comp.toArray(new String[0]);
    }
    public String getThumbnail(String id) {
        String[] conv = wget.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&maxResults=30&key=" + apikey).split("\n");
        String toret = "";
        for(String s : conv) {
            if(s.contains("\"url\"")) {
                toret = s.replace("\"url\"", "").replace("\"", "").replace("          : ", "").replace(",", "").replace("  ", "").replace(" ", "\n");
                break;
            }
        }
        return toret;
    }
    public String getThumbnailMedium(String id) {
        String[] conv = wget.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + apikey).split("\n");
        String toret = "";
        for(String s : conv) {
            if(s.contains("mqdefault.jpg")) {
                toret = s.replace("\"url\"", "").replace("\"", "").replace("          : ", "").replace(",", "").replace("  ", "").replace(" ", "\n");
            }
        }
        return toret;
    }
    public String getThumbnailHigh(String id) {
        String[] conv = wget.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + apikey).split("\n");
        String toret = "";
        for(String s : conv) {
            if(s.contains("hqdefault.jpg")) {
                toret = s.replace("\"url\"", "").replace("\"", "").replace("          : ", "").replace(",", "").replace("  ", "").replace(" ", "\n");
            }
        }
        return toret;
    }
    public String getDescription(String id) {
        String[] conv = wget.get("https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + apikey).split("\n");
        String description = "";
        for(String s : conv) {
            String[] conv1 = s.split(",");
            for(String s2 : conv1) {
                if(s2.contains("\"description\": \"")) {
                    description = s2.replace("\"description\": \"", "").replace("\"", "").replace("          ", "");
                }
            }
        }
        return description;
    }

    public String generateUrl(String id) {
        return "https://youtube.com/watch?v=" + id;
    }
}
