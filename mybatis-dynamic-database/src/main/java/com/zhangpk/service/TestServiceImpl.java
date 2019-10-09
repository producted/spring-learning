package com.zhangpk.service;

import com.zhangpk.config.DataSourceTarget;
import com.zhangpk.mapper.OneMapper;
import com.zhangpk.mapper.TwoMapper;
import com.zhangpk.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 13:47
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private OneMapper oneMapper;
    @Autowired
    private TwoMapper twoMapper;


    @Override
    public List<User> selectFromOne() {
        System.out.println("from master datasource");
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
    @DataSourceTarget(value = "nacos")
    public List<User> selectFromTwo() {
        System.out.println("from nacos datasource");
        return twoMapper.selectFromTwo();
    }


}
