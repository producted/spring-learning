package com.zhangpk.service.impl;

import com.zhangpk.mapper.dbone.OneMapper;
import com.zhangpk.mapper.dbtwo.TwoMapper;
import com.zhangpk.pojo.User;
import com.zhangpk.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 13:47
 */
@Component
public class TestServiceImpl implements TestService {

    @Autowired
    private OneMapper oneMapper;
    @Autowired
    private TwoMapper twoMapper;


    @Override
    public List<User> selectFromOne() {
        return oneMapper.selectFromOne();
    }

    @Override
    public int insertFromOne(User user) {
        return oneMapper.insertFromOne(user);
    }

    @Override
    public int insertFromTwo(User user) {
        return twoMapper.insertFromTwo(user);
    }

    @Override
    public List<User> selectFromTwo() {
        return twoMapper.selectFromTwo();
    }


}
