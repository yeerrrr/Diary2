package com.example.diary.Controller;

import com.example.diary.entity.Lable;
import com.example.diary.entity.TimeData;
import com.example.diary.mapper.DeleteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class DeleteController {
    @Autowired
    private DeleteMapper delete;

    @Value("C:/Users/yeron/Desktop/diary/static/") // 获取配置文件中的指定位置
    private String uploadPath;

    @PostMapping("/delete")
    public String deleteContext(int account, TimeData timeData){
        String year = timeData.getYear();
        String month = timeData.getMonth();
        String day = timeData.getDay();
        String hour = timeData.getHour();
        String minute = timeData.getMinute();
        String moment = hour + "-" + minute;

        File dir = new File(uploadPath + "/" + account + "/"+ year + "/" + month + "/" + day + "/" + moment);
        boolean b = FileSystemUtils.deleteRecursively(dir);
        List<Lable> labellist = delete.getlablist(timeData,account);
        boolean is_delete = delete.delete(timeData,account);
        for(int i=0;i<labellist.size();i++){
            String labname = labellist.get(i).getName();
            delete.updatelab(account,labname);
        }
        if(b && is_delete) {
            return "删除成功！";
        }
        else{
            return "删除失败！";
        }
    }
}
