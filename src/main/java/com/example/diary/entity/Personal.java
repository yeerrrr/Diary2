package com.example.diary.entity;

public class Personal {
    private String username;
    private int account;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "username='" + username + '\'' +
                ", account=" + account +
                '}';
    }
}
