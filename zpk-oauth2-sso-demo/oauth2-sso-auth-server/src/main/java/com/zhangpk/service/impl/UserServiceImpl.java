package com.zhangpk.service.impl;

import com.zhangpk.entity.SysUser;
import com.zhangpk.repository.SysUserRepository;
import com.zhangpk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangpk
 * @date 2019-06-26
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
}
