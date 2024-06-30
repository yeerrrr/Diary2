package com.example.diary.entity;

public class labelContent {
    private ConData conData;
    private TimeData time;
    public ConData getConData() {
        return conData;
    }

    public void setConData(ConData conData) {
        this.conData = conData;
    }

    public TimeData getTime() {
        return time;
    }

    public void setTime(TimeData time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LabelContent{" +
                "conData=" + conData +
                ", time=" + time +
                '}';
    }
}
