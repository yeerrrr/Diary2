package com.example.diary.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class ContextWithoutFile {
    private String[] labnames;
    private int account;
    private String text;

    public String[] getLabnames() {
        return labnames;
    }

    public void setLabnames(String[] labnames) {
        this.labnames = labnames;
    }

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

    @Override
    public String toString() {
        return "ContextWithoutFile{" +
                "labnames=" + Arrays.toString(labnames) +
                ", account=" + account +
                ", text='" + text + '\'' +
                '}';
    }
}
