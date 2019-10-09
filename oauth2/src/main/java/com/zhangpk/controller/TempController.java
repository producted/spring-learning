package com.zhangpk.controller;

import com.zhangpk.util.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created By zhangpk On 2019/6/19
 **/
@RestController
@Slf4j
public class TempController {


    /*@RequestMapping("/user")
    public String user(){
        return "my name is zhangpk ----------------------------------";
    }*/

    @GetMapping("/user")
    public Object getCurrentUser1(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JsonUtil.toJson(authentication));

        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey("merryyou".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String blog = (String) claims.get("blog");
        log.info("【SecurityOauth2Application】 getCurrentUser1 blog={}", blog);

        return authentication;
    }

}
