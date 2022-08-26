package com.player;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import com.github.kiulian.downloader.model.videos.quality.VideoQuality;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Client {
    public String minimalGet(String id) {
        AtomicReference<String> toret = new AtomicReference<>("");
        String videoId = id; // for url https://www.youtube.com/watch?v=abc12345
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo request = new RequestVideoInfo(videoId).async();
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();
        List<VideoWithAudioFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        videoWithAudioFormats.forEach(it -> {
            if(it.videoQuality()!=VideoQuality.hd1080&&it.videoQuality()!=VideoQuality.hd720) {
                toret.set(it.url());
                return;
            }
        });
        return toret.get();
    }
    public String get(String id, VideoQuality quality) {
        AtomicReference<String> toret = new AtomicReference<>("");
        String videoId = id; // for url https://www.youtube.com/watch?v=abc12345
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo request = new RequestVideoInfo(videoId).async();
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();
        List<VideoWithAudioFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        videoWithAudioFormats.forEach(it -> {
            if(it.videoQuality()==quality) {
                toret.set(it.url());
                return;
            }else{
                toret.set(it.url());return;
            }
        });
        return toret.get();
    }
    public String get(String id) {
        AtomicReference<String> toret = new AtomicReference<>("");
        String videoId = id; // for url https://www.youtube.com/watch?v=abc12345
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo request = new RequestVideoInfo(videoId).async();
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();
        List<VideoWithAudioFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        videoWithAudioFormats.forEach(it -> {
            if(it.videoQuality()== VideoQuality.hd1080) {
                toret.set(it.url());
                return;
            }else{
                if(it.videoQuality()==VideoQuality.hd720) {
                    toret.set(it.url());
                    return;
                }else{
                    //If its below 720p quality
                    toret.set(it.url());
                }
            }
        });
        return toret.get();
    }
}
