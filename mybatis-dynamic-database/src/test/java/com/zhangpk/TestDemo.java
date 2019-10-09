package com.zhangpk;

import com.zhangpk.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/7/23 11:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    private TestService testService;

    @Test
    public void test(){
        System.out.println(testService.selectFromOne());
        System.out.println(testService.selectFromTwo());
    }
}
