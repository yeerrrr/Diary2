package com.example.diary.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.diary.entity.Account;
import com.example.diary.entity.Personal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    @Select("select * from account where id = #{id}")
    Account findByAccount(int id);
    @Select("select id,username from account where id = #{account}")
    Personal findByA(int account);
    @Select("select * from account where email = #{email}")
    Account findByEmail(String email);

}