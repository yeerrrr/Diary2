package com.example.diary.entity;

import java.util.List;

public class singleData {
    private time time;
    private String text;
    private List<label> labels;
    private List<String> pics;  //用于存储图片地址
    private music music;
    private List<String> videos;

    public time getTime() {
        return time;
    }

    public void setTime(time nowtime) {
        this.time = nowtime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public List<label> getLabels() {
        return labels;
    }

    public void setLabels(List<label> labels) {
        this.labels = labels;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public com.example.diary.entity.music getMusic() {
        return music;
    }

    public void setMusic(com.example.diary.entity.music music) {
        this.music = music;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
}
