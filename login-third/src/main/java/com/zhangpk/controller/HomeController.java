package com.zhangpk.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhangpk.constant.ConfigConstant;
import com.zhangpk.utils.RemoteRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created By zhangpk On 2019/6/21
 **/
@Controller
@Slf4j
public class HomeController {


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/callback")
    public String callback(String code, Model model){
        log.info("返回code值为：{}", code);
        String url = "https://github.com/login/oauth/access_token";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", ConfigConstant.CLIENT_ID);
        map.add("client_secret", ConfigConstant.CLIENT_SECRET);
        map.add("code", code);
        // 返回的token信息
        JSONObject jsonObject = RemoteRequestUtil.post(url, map, JSONObject.class);
        System.out.println("jsonObject:" + jsonObject);
        String token = jsonObject.getString("access_token");
        // github用户信息
        // 个人理解此时拿到用户信息后可以向数据库内插入一条个人信息，赋予其对应角色，这个时候就可以走security模式了
        String result = RemoteRequestUtil.get("https://api.github.com/user?access_token=" + token);
        model.addAttribute("result", result);
        return "success";
    }

    /*@GetMapping("user/info")
    public String getGitHubUserInfo(String token, Model model) {
        String url = "https://api.github.com/user?access_token=" + token;
        String result = RemoteRequestUtil.get(url);
        model.addAttribute("result", result);
        return "success";
    }*/

    @RequestMapping("/success")
    public String success(){
        log.info("您登录成功了！");
        return "success";
    }
}
