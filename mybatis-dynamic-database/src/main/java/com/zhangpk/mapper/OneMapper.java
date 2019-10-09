package com.zhangpk.mapper;

import com.zhangpk.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 13:47
 */
public interface OneMapper {


    @Select("select * from user")
    List<User> selectFromOne();

    @Insert("insert into user (username, password) values (#{username}, #{password})")
    int insertFromOne(User user);
}
