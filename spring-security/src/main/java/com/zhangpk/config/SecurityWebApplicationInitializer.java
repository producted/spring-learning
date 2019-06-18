package com.zhangpk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created By zhangpk On 2019/6/17
 **/
//@Configuration
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer(){
        super(WebSecurityConfig.class);
    }
}
