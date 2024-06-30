package com.example.diary.Controller;

import com.example.diary.entity.Lable;
import com.example.diary.mapper.LableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LableController {
    @Autowired  //自动注入LableMapper
    private LableMapper lableMapper;
    @GetMapping("/labelAll")
    public List<Lable> query(int account){
        System.out.println(account);
        List<Lable> listlable = lableMapper.findlable(account);
        System.out.println(listlable);
        return listlable;
    }
    @PostMapping("/label")
    public String save(@RequestBody Lable lable){
        System.out.println(lable);
        int i = lableMapper.insert(lable);
        if(i>0){
            return "标签添加成功！";
        }else{
            return "标签添加失败！";
        }
    }
    @PostMapping("/labelchange")
    public String labnamechange(String newname, String oldname, int account){
        boolean sure1 = lableMapper.labchange(newname,oldname,account);
        boolean sure2 = lableMapper.contextchange(newname,oldname,account);
        if(sure1 && sure1) {
            return "修改成功！";
        }
        else return "修改失败！";
    }
}
