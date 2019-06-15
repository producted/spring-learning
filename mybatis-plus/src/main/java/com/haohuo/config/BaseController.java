package com.haohuo.config;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 演示用而已，实际环境该加别的加别的
 * <p>
 * <p>
 * Created by zhangpk on 19/1/26.
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected int getApiCloudAppId() {
        Object appId = request.getAttribute("appId");
        try {
            return (int) appId;
        } catch (Exception e) {
            //默认的appId
            return 10000001;
        }
    }

    protected String getHHVersion() {
        try {
            return request.getHeader("hh_version");
        } catch (Exception e) {
        }
        return "";
    }

    protected boolean getHHIsAndroid() {
        try {
            if ("Android".equals(request.getHeader("hh_os"))) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    protected boolean getHHIsIos() {
        try {
            if ("iOS".equals(request.getHeader("hh_os"))) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

}
