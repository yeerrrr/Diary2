package com.example.diary.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("label")
public class Lable {
    private String name;
    private String intro;
    private String type;
    private String bk;
    private String tc;
    private String visable;
    private int account;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int num) {
        this.number = num;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getVisable() {
        return visable;
    }

    public void setVisable(String visable) {
        this.visable = visable;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Lable{" +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", type='" + type + '\'' +
                ", bk='" + bk + '\'' +
                ", tc='" + tc + '\'' +
                ", visable='" + visable + '\'' +
                ", account=" + account +
                ", number=" + number +
                '}';
    }
}
