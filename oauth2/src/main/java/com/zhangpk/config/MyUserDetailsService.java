package com.zhangpk.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhangpk On 2019/6/19
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        // you can select this user's role in your project
//        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
