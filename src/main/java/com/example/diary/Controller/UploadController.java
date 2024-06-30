package com.example.diary.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UploadController {

    @Value("D:/IDEA/DiaryData") // 获取配置文件中的指定位置
    private String uploadPath;

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, String id) throws IOException {
        if (file.isEmpty()) {
            return "文件为空";
        }

        String originalFilename = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //也可以换格式，因为不确定最终的文件夹是按月日月存还是日期，最后就是这个会影响到下面的预览，预览我试了几次好像没成功，可能还需要调整一下

        String currentDate = dateFormat.format(new Date());
        File dir = new File(uploadPath + "/" + id + "/"+ currentDate);
        System.out.println(uploadPath + "/" + id + "/"+ currentDate);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String newFilename = System.currentTimeMillis() + "." + extension;
        file.transferTo(new File(dir, newFilename));

        return "文件上传成功";
    }


    @GetMapping("/preview/{date}/{filename:.+}")
    public void previewFile(@PathVariable("date") String date,
                            @PathVariable("filename") String filename,
                            HttpServletResponse response) throws IOException {
        File file = new File(uploadPath + "/" + date + "/" + filename);
        if (file.exists() && file.isFile()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
            try (FileInputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        }
    }
}
