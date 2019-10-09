package com.zhangpk.controller;

import com.zhangpk.pojo.User;
import com.zhangpk.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 13:46
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;


    /**
     * 纯属为了区分才这样命名接口
     * @return
     */
    @RequestMapping(value = "/one/selectFromOne")
    public List<User> selectFromOne(){
        return testService.selectFromOne();
    }

    @RequestMapping(value = "/one/insertFromOne")
    public int insertFromOne(){
        return testService.insertFromOne(getUser());
    }

    //***********************************split*******************************************************

    @RequestMapping(value = "/two/selectFromTwo")
    public List<User> selectFromTwo(){
        return testService.selectFromTwo();
    }

    @RequestMapping(value = "/two/insertFromTwo")
    public int insertFromTwo(){
        return testService.insertFromTwo(getUser());
    }

    /**
     * 模拟数据
     * @return
     */
    private User getUser(){
        String[] firstName = {"张","李","王","赵","武","许","洪","程"};
        String[] lastName = {"发的","回滚","如果","能够","是是","确认","就那","不v","破","单笔","工程"};

        User user = new User();
        user.setUsername(firstName[(int)(Math.random() * firstName.length)] + lastName[(int)(Math.random() * lastName.length)]);
        user.setPassword(String.valueOf(Math.random() * 1000000));
        return user;
    }

}
