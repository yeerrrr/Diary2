package com.example.diary.Controller;

import com.example.diary.entity.Account;
import com.example.diary.entity.Personal;
import com.example.diary.entity.login;
import com.example.diary.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class AccountController {
    private String defaultAvatarPath = "classpath:static/adhe.jpg"; // 默认头像在static文件夹中的路径; // 配置文件中的默认头像路径

    private String defaultAvatarDestination = "D:/IDEA/DiaryData/"; // 初始化默认路径

    @Autowired
    private ResourceLoader resourceLoader;


    @Autowired
    private AccountMapper accountMapper;
    @PostMapping("/login")
    public String login(@RequestBody login login) {
        Account account1 = accountMapper.findByAccount(login.getAccount());
        if (account1 == null ) {
            return "账号不存在";
        }
        else if(!account1.getPassword().equals(login.getPassword())){
            return "密码错误";
        }
        else{
            return "登录成功";
        }

    }

    @PostMapping( "/register")
    public int register(@RequestBody Account account)throws IOException {
        if (accountMapper.findByEmail(account.getEmail()) != null) {
            return 0;
        }
        accountMapper.insert(account);
        Resource resource = resourceLoader.getResource(defaultAvatarPath);
        defaultAvatarDestination = defaultAvatarDestination+account.getId()+"/head/adhe.jpg";
        if (resource.exists()) {
            File destinationFolder = new File(defaultAvatarDestination).getParentFile();
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
                System.out.println("目标文件夹不存在，已创建：" + destinationFolder.getAbsolutePath());
            }
            try (InputStream inputStream = resource.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(defaultAvatarDestination)) {
                FileCopyUtils.copy(inputStream, outputStream);
                System.out.println("默认头像已保存到默认路径：" + defaultAvatarDestination);
            }
        } else {
            System.out.println("默认头像文件不存在");
        }
        Account a = accountMapper.findByEmail(account.getEmail());
        // 打印默认头像文件路径
        System.out.println("默认头像文件路径: " + defaultAvatarPath);
        return a.getId();
    }
    @PostMapping("/Findquestion")
    public String ShowQusetion(@RequestParam String email)
    {
        Account account = accountMapper.findByEmail(email);
        if (account != null) {
            String question = account.getQuestion();
            return question;
        }
        return "邮箱错误";
    }

    @PostMapping("/forget")
    public String forget(@RequestParam String email,
                         @RequestParam String answer,@RequestParam String password,@RequestParam String password1) {
        Account account = accountMapper.findByEmail(email);
        if (account != null && account.getEmail().equals(email)
                && account.getAnswer().equals(answer)) {
            if(password1==password){
                account.setPassword(password);
                return "密码重置成功";
            }
            else{
                return "两次密码不相同，请重新输入";  //清空两个输入密码的地方
            }
            //改密码还没设定，待探讨
        }
        return "验证失败，请确认信息是否正确";
    }
    @GetMapping("/personal")
    public Personal getPersonal(int account){
        Personal personal = accountMapper.findByA(account);
        return personal;
    }
}
