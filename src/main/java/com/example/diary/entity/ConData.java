package com.example.diary.entity;

import java.util.List;

public class ConData {
    private List<label> labels;
    private String text;
    private List<String> pics;
    private List<String> videos;
    private MusicData music;

    public List<label> getLabels() {
        return labels;
    }

    public void setLabels(List<label> labels) {
        this.labels = labels;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public MusicData getMusic() {
        return music;
    }

    public void setMusic(MusicData music) {
        this.music = music;
    }

    @Override
    public String toString() {
        return "ConData{" +
                "labels=" + labels +
                ", text='" + text + '\'' +
                ", pics=" + pics +
                ", videos=" + videos +
                ", music=" + music +
                '}';
    }
}
