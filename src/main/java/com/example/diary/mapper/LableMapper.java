package com.example.diary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.diary.entity.Lable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LableMapper extends BaseMapper<Lable> {
    @Select("select * from label where account=#{account}")
    public List<Lable> findlable(int account);
    @Update("update javatest.label set name=#{newname} where account=#{account} and name=#{oldname}")
    public boolean labchange(String newname, String oldname, int account);
    @Update("update javatest.context set name=#{newname} where account=#{account} and name=#{oldname}")
    public boolean contextchange(String newname, String oldname, int account);
}
