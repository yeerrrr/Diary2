package com.example.diary.mapper;

import com.example.diary.entity.ContextTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NewContextMapper {
    @Update("update javatest.label set number=number+1 where account=#{account} and name=#{name}")
    public void Update(int account, String name);

    @Insert("insert into context values(#{index},#{name},#{year},#{month},#{day},#{hour},#{minute},#{account},#{data})")
    public void Insert(ContextTable contextTable);
}
