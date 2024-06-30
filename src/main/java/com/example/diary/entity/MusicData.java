package com.example.diary.entity;

public class MusicData {
    private String name;
    private String src;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "MusicData{" +
                "name='" + name + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
