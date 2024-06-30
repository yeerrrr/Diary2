package com.example.diary.mapper;

import com.example.diary.entity.Lable;
import com.example.diary.entity.TimeData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeleteMapper {
    @Delete("delete from javatest.context where year=#{timeData.year} and month=#{timeData.month} and day=#{timeData.day} \n" +
            "and hour=#{timeData.hour} and minute=#{timeData.minute} and account=#{account}")
    public boolean delete(TimeData timeData, int account);
    @Select("select * from javatest.context,javatest.label where year=#{timeData.year} and month=#{timeData.month} and day=#{timeData.day} \n" +
            "and hour=#{timeData.hour} and minute=#{timeData.minute} and label.account=#{account} and label.name=context.name")
    public List<Lable> getlablist(TimeData timeData, int account);
    @Update("update label set number=number-1 where account=#{account} and name=#{name}")
    public boolean updatelab(int account, String name);
}
