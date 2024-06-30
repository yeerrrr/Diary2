package com.example.diary.Controller;

import com.example.diary.entity.Lable;
import com.example.diary.mapper.Most3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class Most3Controller {
    @Autowired
    private Most3Mapper most3Mapper;

    @GetMapping("/most3")
    public List<Lable> searchMost3(int account){
        List<Lable> lableList = most3Mapper.getMost3(account);
        Collections.sort(lableList,new LableComparator());
        return lableList;
    }

    class LableComparator implements Comparator<Lable>{
        @Override
        public int compare(Lable l1, Lable l2){
            return l2.getNumber() - l1.getNumber();
        }
    }
}
