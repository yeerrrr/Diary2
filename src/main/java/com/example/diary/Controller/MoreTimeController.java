package com.example.diary.Controller;

import com.example.diary.entity.*;
import com.example.diary.mapper.TimeShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MoreTimeController {
    @Autowired
    private TimeShowMapper timeShowMapper;

    private String defaultAvatarDestination = "C:/Users/yeron/Desktop/diary/static/"; // 初始化默认路径,有需要可改

    @GetMapping("/lastweek")  //上一周的接口
    public List<contentData> Lastweek(int account){
        SimpleDateFormat year1 = new SimpleDateFormat("yyyy");
        String y1 = year1.format(new Date());
        SimpleDateFormat month1 = new SimpleDateFormat("MM");
        String m1 = month1.format(new Date());
        SimpleDateFormat day1 = new SimpleDateFormat("dd");
        String d1 = day1.format(new Date());
        int y11 = Integer.valueOf(y1);
        int m11 = Integer.valueOf(m1);
        int d11 = Integer.valueOf(d1);

        String startYear = "1970";
        String startMonth = "01";
        String startDay = "01";
        int lastday = 0;
        if(d11>=7){
            int a = d11 - 7;
            startDay = Integer.toString(a);
            startMonth = Integer.toString(m11);
            startYear = Integer.toString(y11);
        }
        else if(d11<7&&m11>1){
            switch(m11-1){
                case 1,3,5,7,8,10: lastday = 31-7+d11; break;
                case 2: if((0 == y11 % 4 && y11 % 100 != 0) || (0 == y11 % 400)) lastday = 29-7+d11;
                        else lastday = 28-7+d11;
                        break;
                case 4,6,9,11: lastday = 30-7+d11; break;
            }
            startDay = Integer.toString(lastday);
            startMonth = Integer.toString(m11-1);
            startYear = Integer.toString(y11);
        }
        else if(d11<7&&m11==1){
            startDay = Integer.toString(31-7+d11);
            startMonth = Integer.toString(12);
            startYear = Integer.toString(y11-1);
        }

        List<DataTime> timedatas = new ArrayList<>();
        List<DataTime> ti2 = new ArrayList<>();
        if(startYear!=y1){
            timedatas = timeShowMapper.getByIdAndTimeBetween(account, startYear,startMonth,startYear,"12");
            ti2 = timeShowMapper.getByIdAndTimeBetween(account, y1,"01",y1,m1);
        }
        for(int i = 0;i<ti2.size();i++){
            timedatas.add(ti2.get(i));
        }
        System.out.println(timedatas);

        List<contentData> contentDatas = new ArrayList<>();
        //以天为单位
        for (int i = timedatas.size()-1 ; i >=0 ; ) {
            contentData contentdata = new contentData();
            String year = timedatas.get(i).getYear();
            String month = timedatas.get(i).getMonth();
            String day = timedatas.get(i).getDay();
            //先排除日子不对的
            if(month.equals(startMonth)){
                if(Integer.valueOf(day).intValue()<Integer.valueOf(startDay).intValue())
                    continue;
            }
            if(month.equals(m1)){
                if(Integer.valueOf(day).intValue()>Integer.valueOf(d1).intValue())
                    continue;
            }

            //dataInfo类的加入
            dataInfo da1 = new dataInfo();
            da1.setYear(year);
            da1.setMonth(month);
            da1.setDay(day);
            contentdata.setDateInfo(da1);

            List<DataTime> stm = timeShowMapper.getByHoldTime(year,month,day,account);
            List<singleData> singleDatas = new ArrayList<>();
            //以时刻数为单位
            for(int m=stm.size()-1 ;m>=0;){
                String hour =stm.get(m).getHour();
                String minute = stm.get(m).getMinute();
                String moment = hour + "-" + minute;
                List<label> lab = timeShowMapper.getByName(year,month,day,hour,minute,account);
                //i由标签和时刻的数量决定
                singleData single = new singleData();
                single.setLabels(lab);
                //时刻的存储
                time t1 = new time();
                t1.setHour(hour);
                t1.setMinute(minute);
                single.setTime(t1);

                String originalPath = "static/" + account + "/" + year + "/" + month + "/" + day + "/" + moment;
                File dir = new File(defaultAvatarDestination + account + "/" + year + "/" + month + "/" + day + "/" + moment);
                System.out.println(defaultAvatarDestination + account + "/" + year + "/" + month + "/" + day + "/" + moment);

                List<String> photos = new ArrayList<>();
                List<String> videos = new ArrayList<>();
                music music = new music();
                try {
                    File[] files = dir.listFiles(); //获取该文件夹下所有文件
                    if (files != null){
                        for (int j = 0; j < files.length; j++) {
                            String name = files[j].getName();
                            String extension = StringUtils.getFilenameExtension(name);
                            if (extension.equals("txt")) {
                                StringBuilder str = new StringBuilder();
                                try {
                                    BufferedReader reader = new BufferedReader(new FileReader(files[j]));
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        str.append(line);
                                    }
                                    single.setText(str.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println(str);
                            } else if (extension.equals("jpeg")) {
                                photos.add(originalPath + "/" + name);
                                System.out.println(originalPath + "/" + name);
                            } else if (extension.equals("mp4")) {
                                videos.add(originalPath + "/" + name);
                                System.out.println(originalPath + "/" + name);
                            } else if (extension.equals("mp3")) {
                                music.setSrc(originalPath + "/" + name);
                                music.setName(name);
                                System.out.println(originalPath + "/" + name);
                            }
                        }
                    }
                }catch(NullPointerException e) {
                    e.printStackTrace();
                }

                single.setPics(photos);
                single.setVideos(videos);
                single.setMusic(music);
                singleDatas.add(single);
                m = m-lab.size();
            }
            i = i-stm.size();
            contentdata.setSingleData(singleDatas);
            contentDatas.add(contentdata);
        }
        return contentDatas;
    }

    @GetMapping("/lastmonth")  //上一月的接口
    public List<contentData> LastMonth(int account){
        SimpleDateFormat year1 = new SimpleDateFormat("yyyy");
        String y1 = year1.format(new Date());
        SimpleDateFormat month1 = new SimpleDateFormat("MM");
        String m1 = month1.format(new Date());
        SimpleDateFormat day1 = new SimpleDateFormat("dd");
        String d1 = day1.format(new Date());
        int y11 = Integer.valueOf(y1);
        int m11 = Integer.valueOf(m1);
        int d11 = Integer.valueOf(d1);

        String startYear = "1970";
        String startMonth = "01";
        String startDay = d1;
        if(m11==3&&d11>28){
            startYear = y1;
            startMonth = Integer.toString(m11-1);
            startDay = Integer.toString(28);
        }
        else if(m11==1){
            startMonth = Integer.toString(12);
            startYear = Integer.toString(y11-1);
        }
        else{
            startYear = y1;
            startMonth = Integer.toString(m11-1);
            startDay = d1;
        }

        List<DataTime> timedatas = new ArrayList<>();
        List<DataTime> ti2 = new ArrayList<>();
        if(startYear!=y1){
            timedatas = timeShowMapper.getByIdAndTimeBetween(account, startYear,startMonth,startYear,"12");
            ti2 = timeShowMapper.getByIdAndTimeBetween(account, y1,"01",y1,m1);
        }
        for(int i = 0;i<ti2.size();i++){
            timedatas.add(ti2.get(i));
        }
        System.out.println(timedatas);

        List<contentData> contentDatas = new ArrayList<>();
        //以天为单位
        for (int i = timedatas.size()-1 ; i >=0 ; ) {
            contentData contentdata = new contentData();
            String labels = timedatas.get(i).getName();
            String year = timedatas.get(i).getYear();
            String month = timedatas.get(i).getMonth();
            String day = timedatas.get(i).getDay();
            //先排除日子不对的
            if(month.equals(startMonth)){
                if(Integer.valueOf(day).intValue()<Integer.valueOf(startDay).intValue()) continue;
            }
            if(month.equals(m1)){
                if(Integer.valueOf(day).intValue()>Integer.valueOf(d1).intValue()) continue;
            }

            //dataInfo类的加入
            dataInfo da1 = new dataInfo();
            da1.setYear(year);
            da1.setMonth(month);
            da1.setDay(day);
            contentdata.setDateInfo(da1);

            List<DataTime> stm = timeShowMapper.getByHoldTime(year,month,day,account);
            List<singleData> singleDatas = new ArrayList<>();
            //以时刻数为单位
            for(int m=stm.size()-1 ;m>=0;){
                String hour =stm.get(m).getHour();
                String minute = stm.get(m).getMinute();
                String moment = hour + "-" + minute;
                List<label> lab = timeShowMapper.getByName(year,month,day,hour,minute,account);
                //i由标签和时刻的数量决定
                singleData single = new singleData();
                single.setLabels(lab);
                //时刻的存储
                time t1 = new time();
                t1.setHour(hour);
                t1.setMinute(minute);
                single.setTime(t1);

                String originalPath = "static/" + account + "/" + year + "/" + month + "/" + day + "/" + moment;
                File dir = new File(defaultAvatarDestination + account + "/" + year + "/" + month + "/" + day + "/" + moment);
                System.out.println(defaultAvatarDestination + account + "/" + year + "/" + month + "/" + day + "/" + moment);

                List<String> photos = new ArrayList<>();
                List<String> videos = new ArrayList<>();
                music music = new music();
                try {
                    File[] files = dir.listFiles(); //获取该文件夹下所有文件
                    if (files != null){
                        for (int j = 0; j < files.length; j++) {
                            String name = files[j].getName();
                            String extension = StringUtils.getFilenameExtension(name);
                            if (extension.equals("txt")) {
                                StringBuilder str = new StringBuilder();
                                try {
                                    BufferedReader reader = new BufferedReader(new FileReader(files[j]));
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        str.append(line);
                                    }
                                    single.setText(str.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println(str);
                            } else if (extension.equals("jpeg")) {
                                photos.add(originalPath + "/" + name);
                                System.out.println(originalPath + "/" + name);
                            } else if (extension.equals("mp4")) {
                                videos.add(originalPath + "/" + name);
                                System.out.println(originalPath + "/" + name);
                            } else if (extension.equals("mp3")) {
                                music.setSrc(originalPath + "/" + name);
                                music.setName(name);
                                System.out.println(originalPath + "/" + name);
                            }
                        }
                    }
                }catch(NullPointerException e) {
                    e.printStackTrace();
                }

                single.setPics(photos);
                single.setVideos(videos);
                single.setMusic(music);
                singleDatas.add(single);
                m = m-lab.size();
            }
            i = i-stm.size();
            contentdata.setSingleData(singleDatas);
            contentDatas.add(contentdata);
        }
        return contentDatas;
    }

    @GetMapping("/lastthreemonth")
    public List<contentData> LastThreeMonth(int account){
        SimpleDateFormat year1 = new SimpleDateFormat("yyyy");
        String y1 = year1.format(new Date());
        SimpleDateFormat month1 = new SimpleDateFormat("MM");
        String m1 = month1.format(new Date());
        SimpleDateFormat day1 = new SimpleDateFormat("dd");
        String d1 = day1.format(new Date());
        int y11 = Integer.valueOf(y1);
        int m11 = Integer.valueOf(m1);
        int d11 = Integer.valueOf(d1);

        String startYear = "1970";
        String startMonth = "01";
        String startDay = d1;
        if(m11==3){
            startYear = y1;
            startMonth = Integer.toString(m11-1);
            if(d11>28)
                startDay = Integer.toString(28);
        }
        else if(m11<=3){
            startMonth = Integer.toString(12-3+m11);
            startYear = Integer.toString(y11-1);
        }
        else{
            startYear = y1;
            startMonth = Integer.toString(m11-3);
            if(d11>28)
                startDay = Integer.toString(28);
        }


        List<DataTime> timedatas = new ArrayList<>();
        List<DataTime> ti2 = new ArrayList<>();
        if(startYear!=y1){
            timedatas = timeShowMapper.getByIdAndTimeBetween(account, startYear,startMonth,startYear,"12");
            ti2 = timeShowMapper.getByIdAndTimeBetween(account, y1,"01",y1,m1);
        }
        for(int i = 0;i<ti2.size();i++){
            timedatas.add(ti2.get(i));
        }
        System.out.println(timedatas);

        List<contentData> contentDatas = new ArrayList<>();
        //以天为单位
        for (int i = timedatas.size()-1 ; i >=0 ; ) {
            contentData contentdata = new contentData();
            String labels = timedatas.get(i).getName();
            String year = timedatas.get(i).getYear();
            String month = timedatas.get(i).getMonth();
            String day = timedatas.get(i).getDay();
            //先排除日子不对的
            if(month.equals(startMonth)){
                if(Integer.valueOf(day).intValue()<Integer.valueOf(startDay).intValue()) continue;
            }
            if(month.equals(m1)){
                if(Integer.valueOf(day).intValue()>Integer.valueOf(d1).intValue()) continue;
            }

            //dataInfo类的加入
            dataInfo da1 = new dataInfo();
            da1.setYear(year);
            da1.setMonth(month);
            da1.setDay(day);
            contentdata.setDateInfo(da1);

            List<DataTime> stm = timeShowMapper.getByHoldTime(year,month,day,account);
            List<singleData> singleDatas = new ArrayList<>();
            //以时刻数为单位
            for(int m=stm.size()-1 ;m>=0;){
                String hour =stm.get(m).getHour();
                String minute = stm.get(m).getMinute();
                String moment = hour + "-" + minute;
                List<label> lab = timeShowMapper.getByName(year,month,day,hour,minute,account);
                //i由标签和时刻的数量决定
                singleData single = new singleData();
                single.setLabels(lab);
                //时刻的存储
                time t1 = new time();
                t1.setHour(hour);
                t1.setMinute(minute);
                single.setTime(t1);

                String originalPath = "static/" + account + "/" + year + "/" + month + "/" + day + "/" + moment;
                File dir = new File(defaultAvatarDestination + account + "/" + year + "/" + month + "/" + day + "/" + moment);
                System.out.println(defaultAvatarDestination + account + "/" + year + "/" + month + "/" + day + "/" + moment);

                List<String> photos = new ArrayList<>();
                List<String> videos = new ArrayList<>();
                music music = new music();
                try {
                    File[] files = dir.listFiles(); //获取该文件夹下所有文件
                    if (files != null){
                        for (int j = 0; j < files.length; j++) {
                            String name = files[j].getName();
                            String extension = StringUtils.getFilenameExtension(name);
                            if (extension.equals("txt")) {
                                StringBuilder str = new StringBuilder();
                                try {
                                    BufferedReader reader = new BufferedReader(new FileReader(files[j]));
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        str.append(line);
                                    }
                                    single.setText(str.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println(str);
                            } else if (extension.equals("jpeg")) {
                                photos.add(originalPath + "/" + name);
                                System.out.println(originalPath + "/" + name);
                            } else if (extension.equals("mp4")) {
                                videos.add(originalPath + "/" + name);
                                System.out.println(originalPath + "/" + name);
                            } else if (extension.equals("mp3")) {
                                music.setSrc(originalPath + "/" + name);
                                music.setName(name);
                                System.out.println(originalPath + "/" + name);
                            }
                        }
                    }
                }catch(NullPointerException e) {
                    e.printStackTrace();
                }

                single.setPics(photos);
                single.setVideos(videos);
                single.setMusic(music);
                singleDatas.add(single);
                m = m-lab.size();
            }
            i = i-stm.size();
            contentdata.setSingleData(singleDatas);
            contentDatas.add(contentdata);
        }
        return contentDatas;
    }
}
