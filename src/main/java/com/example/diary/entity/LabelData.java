package com.example.diary.entity;

public class LabelData {
    private String bk;
    private String tc;
    private String name;
    private String intro;
    private int number;
    private String type;

    public String getBk() {
        return bk;
    }

    public void setBk(String bk) {
        this.bk = bk;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LabelData{" +
                "bk='" + bk + '\'' +
                ", tc='" + tc + '\'' +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", number=" + number +
                ", type='" + type + '\'' +
                '}';
    }
}
