package com.example.diary.Controller;

import com.example.diary.entity.*;
import com.example.diary.mapper.TimeShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeShowController {
    @Autowired
    private TimeShowMapper timeShowMapper;

    private String defaultAvatarDestination = "C:/Users/yeron/Desktop/diary/static/"; // 初始化默认路径

    @GetMapping("/showtime")  //准确的时间限制的接口
    public List<contentData> getTimeData(@RequestParam int account, @RequestParam String starttime, @RequestParam String endtime) {
        String startYear = starttime.substring(0, 4);
        String startMonth = starttime.substring(5, 7);
        String startDay = starttime.substring(8, 10);
        String endYear = endtime.substring(0, 4);
        String endMonth = endtime.substring(5, 7);
        String endDay = endtime.substring(8 ,10);
        List<DataTime> timedatas = new ArrayList<>();
        List<DataTime> ti2 = new ArrayList<>();
        if(startYear!=endYear){
            timedatas = timeShowMapper.getByIdAndTimeBetween(account, startYear,startMonth,startYear,"12");
            ti2 = timeShowMapper.getByIdAndTimeBetween(account, endYear,"01",endYear,endMonth);
        }
        for(int i = 0;i<ti2.size();i++){
            timedatas.add(ti2.get(i));
        }

        System.out.println(timedatas);

        List<contentData> contentDatas = new ArrayList<>();
        if(Integer.valueOf(startYear)>Integer.valueOf(endYear)||(Integer.valueOf(startYear)==Integer.valueOf(endYear)&&Integer.valueOf(startMonth)>Integer.valueOf(endMonth))||(Integer.valueOf(startYear)<Integer.valueOf(endYear)&&Integer.valueOf(startMonth)==Integer.valueOf(endMonth)&&Integer.valueOf(startDay)>Integer.valueOf(endDay))){
            return null;
        }
            //以天为单位
            for (int i = timedatas.size() - 1; i >= 0; ) {
                contentData contentdata = new contentData();
                String labels = timedatas.get(i).getName();
                String year = timedatas.get(i).getYear();
                String month = timedatas.get(i).getMonth();
                String day = timedatas.get(i).getDay();
                //先排除日子不对的
                if (month.equals(startMonth)) {
                    if (Integer.valueOf(day).intValue() < Integer.valueOf(startDay).intValue()) continue;
                }
                if (month.equals(endMonth)) {
                    if (Integer.valueOf(day).intValue() > Integer.valueOf(endDay).intValue()) continue;
                }

                //dataInfo类的加入
                dataInfo d1 = new dataInfo();
                d1.setYear(year);
                d1.setMonth(month);
                d1.setDay(day);
                contentdata.setDateInfo(d1);

                List<DataTime> stm = timeShowMapper.getByHoldTime(year, month, day, account);
                List<singleData> singleDatas = new ArrayList<>();
                //以时刻数为单位
                for (int m = stm.size() - 1; m >= 0; ) {
                    String hour = stm.get(m).getHour();
                    String minute = stm.get(m).getMinute();
                    String moment = hour + "-" + minute;
                    List<label> lab = timeShowMapper.getByName(year, month, day, hour, minute, account);
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
                        if (files != null) {
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
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    single.setPics(photos);
                    single.setVideos(videos);
                    single.setMusic(music);
                    singleDatas.add(single);
                    m = m - lab.size();
                }
                i = i - stm.size();
                contentdata.setSingleData(singleDatas);
                contentDatas.add(contentdata);
            }
            return contentDatas;
        }
}
