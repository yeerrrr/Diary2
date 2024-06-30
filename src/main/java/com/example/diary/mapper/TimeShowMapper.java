package com.example.diary.mapper;

import com.example.diary.entity.DataTime;
import com.example.diary.entity.label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
@Mapper
public interface TimeShowMapper {
    @Select("select name,year,month,day,hour,minute,data from javatest.context where account = #{account} and year BETWEEN #{startYear} AND #{endYear}  and month BETWEEN #{startMonth} AND #{endMonth} ")
    List<DataTime> getByIdAndTimeBetween(int account, String startYear, String startMonth, String endYear, String endMonth);
    @Select("select name,year,month,day,hour,minute,data from javatest.context where account = #{account} and year = #{year}  and month =#{month} and day = #{day} ")
    List<DataTime> getByHoldTime(String year,String month,String day,int account);
    @Select(
    "select label.bk,label.tc,label.name from javatest.context,javatest.label where year=#{year} and month=#{month} and day=#{day} \n" +
            "            and hour=#{hour} and minute=#{minute} and label.account=#{account} and label.name=context.name")
    List<label> getByName(String year, String month, String day, String hour, String minute, int account);
    @Select("select * from javatest.context where account=#{account}")
    public List<DataTime> getAll(int account);
}
