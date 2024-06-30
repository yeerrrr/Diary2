package com.example.diary.Controller;

import com.example.diary.entity.ContextTable;
import com.example.diary.entity.ContextWithoutFile;
import com.example.diary.entity.NewContext;
import com.example.diary.mapper.NewContextMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class NewContextWithoutFileController {
    @Autowired
    private NewContextMapper newContextMapper;

    @Value("C:/Users/yeron/Desktop/diary/static") // 获取配置文件中的指定位置
    private String uploadPath;

    private ContextTable contextTable = new ContextTable();

    @PostMapping(value = "/newContextWithoutFile")
    public String upnewcontext(ContextWithoutFile contextWithoutFile) throws IOException {
        System.out.println(contextWithoutFile);
        String[] labname = contextWithoutFile.getLabnames();
        int account = contextWithoutFile.getAccount();
        String context = contextWithoutFile.getText();
        contextTable.setAccount(account);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //也可以换格式，因为不确定最终的文件夹是按月日月存还是日期，最后就是这个会影响到下面的预览，预览我试了几次好像没成功，可能还需要调整一下
        String currentDate = dateFormat.format(new Date());
        String year = currentDate.substring(0, 4);
        String month = currentDate.substring(4, 6);
        String day = currentDate.substring(6, 8);
        String hour = currentDate.substring(8, 10);
        String minute = currentDate.substring(10, 12);
        String moment = hour + "-" + minute;
        contextTable.setYear(year);
        contextTable.setMonth(month);
        contextTable.setDay(day);
        contextTable.setHour(hour);
        contextTable.setMinute(minute);
        System.out.println(year + " " + month + " " + day + " " + moment);
        File dir = new File(uploadPath + "/" + account + "/" + year + "/" + month + "/" + day + "/" + moment);
        System.out.println(uploadPath + "/" + account + "/" + year + "/" + month + "/" + day + "/" + moment);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileWriter writer = new FileWriter(uploadPath + "/" + account + "/" + year + "/" + month + "/" + day + "/" + moment + "/context.txt");
        writer.write(context);
        writer.close();

        for (int i = 0; i < labname.length; i++) {
            newContextMapper.Update(account, labname[i]);
            contextTable.setName(labname[i]);
            newContextMapper.Insert(contextTable);
        }
        return "发布成功！";
    }
}
