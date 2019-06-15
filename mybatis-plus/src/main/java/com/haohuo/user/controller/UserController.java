package com.haohuo.user.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haohuo.user.bean.UserModel;
import com.haohuo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangpk
 * @since 2019-06-14
 */
@RestController
@RequestMapping("/user")
@Api(value = "first api", tags = "测试接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getByWhere")
    @ApiOperation(value = "查询用户")
    public List<UserModel> getByWhere(UserModel userModel){
        // 这里按10条分页吧，具体实践时可以让前端传入
        Page<UserModel> page = new Page<>(1, 10);
        return userService.getByWhere(page,userModel);
    }

}

