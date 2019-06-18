package com.zhangpk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created By zhangpk On 2019/6/17
 **/
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    /**
     * 创建一个名为springSecurityFilterChain的Servlet过滤器，
     * 它负责应用程序中的所有安全性（保护应用程序URL，验证提交的用户名和密码，重定向到日志格式等）
     *
     *
     * 将 UserDetailsService 显示为 Bean
     * 在内存中创建一个名为 "user" 的用户，密码为 "pwd"，拥有 "USER" 权限
     *
     * @Author: zhangpk
     */
    /*@Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // 该方法已过时
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("zhangpk").password("zhangpk").roles("USER").build());
        return manager;
    }*/

    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF - 跨站请求伪造
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasAuthority("USER")// 注意此处，未连接数据库下demo版写的是hasRole，现在是hasAuthority，真尼玛搞了俩小时，一直报403
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    /**
     * 添加 UserDetailService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());// 设置passwordEncoder
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 此方法已过时
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
