package com.example.diary.entity;

import java.util.List;

public class contentData {
    private dataInfo dateInfo;
    private List<singleData> singleData;

    public List<singleData> getSingleData() {
        return singleData;
    }

    public void setSingleData(List<singleData> singleData) {
        this.singleData = singleData;
    }

    public dataInfo getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(dataInfo dateInfo) {
        this.dateInfo = dateInfo;
    }
}
