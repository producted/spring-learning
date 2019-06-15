package com.haohuo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haohuo.user.bean.UserModel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohuo.user.mapper.UserMapper;
import com.haohuo.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangpk
 * @since 2019-06-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserModel> getByWhere(Page<UserModel> page, UserModel userModel) {
        QueryWrapper<UserModel> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userModel.getUsername())) {
            wrapper.like("username",userModel.getUsername());
        }
        return userMapper.selectPage(page, wrapper).getRecords();
//        return userMapper.getByWhere(page,userModel);
    }
}
