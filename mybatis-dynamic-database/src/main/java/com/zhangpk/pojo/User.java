package com.zhangpk.pojo;

import java.io.Serializable;

/**
 * 测试bean
 * 这里假设两个库都有一张user表，但实际运用中并非如此
 *
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 13:42
 */
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String nickname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
