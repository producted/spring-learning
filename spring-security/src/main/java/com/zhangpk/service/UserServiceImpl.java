package com.zhangpk.service;

import com.zhangpk.model.UserDO;
import com.zhangpk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By zhangpk On 2019/6/17
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDO> getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void insert(UserDO userDO) {
        // 由于使用了security的密码加密功能，所以在注册时要对密码加密
        setPassword(userDO);
        userRepository.save(userDO);
    }

    private void setPassword(UserDO userDO){
        String passCode = new BCryptPasswordEncoder().encode(userDO.getPassword());
        userDO.setPassword(passCode);
    }
}
