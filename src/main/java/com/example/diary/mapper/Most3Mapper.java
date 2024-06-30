package com.example.diary.mapper;

import com.example.diary.entity.Lable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Most3Mapper {
    @Select("select * from label where account=#{account}")
    public List<Lable> getMost3(int account);
}
