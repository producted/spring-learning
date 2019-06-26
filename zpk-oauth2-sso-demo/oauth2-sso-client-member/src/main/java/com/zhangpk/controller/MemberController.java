package com.zhangpk.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author zhangpk
 * @date 2019-06-26
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/list")
    public String list() {

        return "member/list";
    }

    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @GetMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }

    @PreAuthorize("hasAuthority('member:save')")
    @ResponseBody
    @PostMapping("/add")
    public String add() {

        return "add";
    }

    /**
     * 这是详情按钮，sql脚本中admin会显示按钮并可点击链接
     * zhangsan无法显示按钮 若要显示按钮可以将security页面标签砍掉 此时点击链接应该会报403 可以定义过滤器来返回健康内容
     * @return
     */
    @PreAuthorize("hasAuthority('member:detail')")
    @ResponseBody
    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }
}
