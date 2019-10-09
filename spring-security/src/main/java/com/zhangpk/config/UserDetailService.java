package com.zhangpk.config;

import com.zhangpk.model.UserDO;
import com.zhangpk.repository.UserRepository;
import com.zhangpk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailsService，spring security认识的这个bean，在websecurityconfig内注入
 * <p>
 * Created By zhangpk On 2019/6/17
 **/
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<UserDO> users = userService.getUserByName(name);
//        List<UserDO> users = userRepository.findByUsername(name);
        if (users == null || users.isEmpty()) {
            throw new RuntimeException("user is not reg");
        }
        UserDO userDO = users.get(0);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        // you can select this user's role in your project
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(userDO.getUsername(), userDO.getPassword(), simpleGrantedAuthorities);
    }
}
