package com.zhangpk.service;

import com.zhangpk.model.UserDO;

import java.util.List;

/**
 * Created By zhangpk On 2019/6/17
 **/
public interface UserService {
    List<UserDO> getUserByName(String name);

    void insert(UserDO userDO);
}
