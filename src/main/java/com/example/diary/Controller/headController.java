package com.example.diary.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class headController {
    @Value("D:/IDEA/DiaryData") // 获取配置文件中的指定位置
    private String uploadPath;

    @PostMapping("/headchange")
    public String headchange(@RequestParam("file") MultipartFile file, int account) throws IOException {
        if (file.isEmpty()) {
            return "头像上传为空！";
        }
        String originalFilename = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String newname = "head." + extension;

        File olddir = new File(uploadPath + "/" + account + "/head/");
        boolean b = FileSystemUtils.deleteRecursively(olddir);
        if(b){
            System.out.println("删除成功");
        }
        File newdir = new File(uploadPath + "/" + account + "/head/");
        if(!newdir.exists()){
            newdir.mkdir();
        }
        file.transferTo(new File(newdir, newname));
        return "上传成功！";
    }
}
