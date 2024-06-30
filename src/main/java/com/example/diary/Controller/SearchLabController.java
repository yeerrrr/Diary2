package com.example.diary.Controller;

import com.example.diary.entity.*;
import com.example.diary.mapper.SearchLabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchLabController {
    @Autowired
    private SearchLabMapper searchLabMapper;

    @Value("C:/Users/yeron/Desktop/diary/static/") // 获取配置文件中的指定位置
    private String uploadPath;

    @GetMapping("/searchbylab")
    public SearchLab searchbylab(int account, String name){
        SearchLab searchLab = new SearchLab();
        List<TimeData> getTime = searchLabMapper.getTime(account,name);
        List<labelContent> LabelContent = new ArrayList<>();
        System.out.println(getTime);
        for(int i=0;i<getTime.size();i++){
            ConData conData = new ConData();
            labelContent LabelTmp = new labelContent();
            String year = getTime.get(i).getYear();
            String month = getTime.get(i).getMonth();
            String day = getTime.get(i).getDay();
            String hour = getTime.get(i).getHour();
            String minute = getTime.get(i).getMinute();
            String moment = hour + "-" + minute;
            String originalPath = "static/" + account + "/" + year + "/" + month + "/" + day + "/" + moment;
            File dir = new File(uploadPath + account + "/" + year + "/" + month + "/" + day + "/" + moment);
            System.out.println(uploadPath + account + "/" + year + "/" + month + "/" + day + "/" + moment);
            LabelTmp.setTime(getTime.get(i));
            List<label> getLab = searchLabMapper.getLab(year,month,day,hour,minute,account);
            conData.setLabels(getLab);

            List<String> photos = new ArrayList<>();
            List<String> videos = new ArrayList<>();
            MusicData musicData = new MusicData();
            File[] files = dir.listFiles(); //获取该文件夹下所有文件
            for(int j=0;j<files.length;j++){
                String filename = files[j].getName();
                String extension = StringUtils.getFilenameExtension(filename);
                if(extension.equals("txt")){
                    StringBuilder str = new StringBuilder();
                    try{
                        BufferedReader reader = new BufferedReader(new FileReader(files[j]));
                        String line;
                        while((line = reader.readLine()) != null){
                            str.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    conData.setText(str.toString());
                    System.out.println(str);
                } else if (extension.equals("jpeg")) {
                    photos.add(originalPath + "/" + filename);
                    System.out.println(originalPath + "/" + filename);
                } else if (extension.equals("mp4")) {
                    videos.add(originalPath + "/" + filename);
                    System.out.println(originalPath + "/" + filename);
                } else if (extension.equals("mp3")) {
                    int pos = filename.indexOf(".");
                    String musicname = filename.substring(0,pos);
                    musicData.setName(musicname);
                    musicData.setSrc(originalPath + "/" + filename);
                    System.out.println(originalPath + "/" + filename);
                }
            }
            conData.setMusic(musicData);
            conData.setPics(photos);
            conData.setVideos(videos);
            LabelTmp.setConData(conData);
            LabelContent.add(LabelTmp);
        }
        searchLab.setLabelContent(LabelContent);
        LabelData Label = searchLabMapper.getIntro(name, account);
        searchLab.setLabel(Label);
        return searchLab;
    }
}
