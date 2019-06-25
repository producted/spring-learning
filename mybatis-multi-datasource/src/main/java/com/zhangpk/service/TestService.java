package com.zhangpk.service;

import com.zhangpk.pojo.User;

import java.util.List;

public interface TestService {
    List<User> selectFromOne();

    List<User> selectFromTwo();

    int insertFromOne(User user);

    int insertFromTwo(User user);
}
