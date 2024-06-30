package com.example.diary.entity;

public class Getday {
    private int day;
    private int month;
    private int year;
    public Getday(int d,int m,int y){
        day = d;
        month = m;
        year = y;
    }
    public int Getlastday(){
        int lastday=0;
        if(day<7&&month>1){
            switch(month-1){
                case 1,3,5,7,8,10: lastday = 31-7+day; break;
                case 2: if((0 == year % 4 && year % 100 != 0) || (0 == year % 400)) lastday = 29-7+day;
                        else lastday = 28-7+day;
                case 4,6,9,11: lastday = 30-7+day;
            }
        }
        return lastday;
    }
}
