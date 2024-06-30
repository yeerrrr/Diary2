package com.example.diary.mapper;

import com.example.diary.entity.LabelData;
import com.example.diary.entity.Lable;
import com.example.diary.entity.TimeData;
import com.example.diary.entity.label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchLabMapper {
    @Select("select year,month,day,hour,minute,data from context where account=#{account} and name=#{name}")
    public List<TimeData> getTime(int account, String name);
    @Select("select * from context,label where year=#{year} and month=#{month} and day=#{day} \n" +
            "and hour=#{hour} and minute=#{minute} and label.account=#{account} and label.name=context.name")
    public List<label> getLab(String year, String month, String day, String hour, String minute, int account);
    @Select("select * from label where name=#{name} and account=#{account}")
    public LabelData getIntro(String name, int account);
}
