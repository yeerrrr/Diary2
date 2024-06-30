package com.example.diary.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class NewContext {
    private String[] labnames;
    private int account;
    private MultipartFile[] files;
    private String text;
    private String data;

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getLabnames() {
        return labnames;
    }

    public void setLabnames(String[] labnames) {
        this.labnames = labnames;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewContext{" +
                "labnames=" + Arrays.toString(labnames) +
                ", account=" + account +
                ", files=" + Arrays.toString(files) +
                ", text='" + text + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
