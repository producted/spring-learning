/*
package com.haohuo;

import com.haohuo.mapper.UserMapper;
import com.haohuo.simple.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

*/
/**
 * Created By zhangpk On 2019/5/29
 **//*

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        System.out.println(("----- selectAll method test ------"));
        List<User> users = userMapper.selectList(null);
        Assert.assertEquals(5,users.size());
        users.forEach(System.out::println);
    }

}
*/
