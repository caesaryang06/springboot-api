package com.yxm.springbootapi.oauth2.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxm.springbootapi.oauth2.utils.HttpClientUtils;
import com.yxm.springbootapi.oauth2.utils.WeiXinUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CAESAR
 * @Classname OauthController
 * @Description TODO
 * @Date 2019-09-22 19:09
 */
@Controller
@Slf4j
public class OauthController {

    @Autowired
    private WeiXinUtils weiXinUtils;


    private String errorPage = "errorPage";


    /**
     * 生成授权链接 重定向跳转到微信开放平台
     * @return
     */
    @RequestMapping("/authorizedUrl")
    public String authorizedUrl(){

        String authorizedUrl = weiXinUtils.getAuthorizedUrl();
        log.info("authorizedUrl={}",authorizedUrl);
        return "redirect:" + authorizedUrl;
    }

    /**
     * 微信授权回调地址
     * @param code
     * @return
     */
    @RequestMapping("/callback")
    public String callback(String code, HttpServletRequest request){
        // 1.使用Code 获取 access_token
        String accessTokenUrl = weiXinUtils.getAccessTokenUrl(code);
        JSONObject resultAccessToken = HttpClientUtils.httpGet(accessTokenUrl);
        boolean containsKey = resultAccessToken.containsKey("errcode");

        if (containsKey) {
            request.setAttribute("errorMsg", "系统错误!");
            return errorPage;
        }
        // 2.使用access_token获取用户信息
        String accessToken = resultAccessToken.getString("access_token");
        String openid = resultAccessToken.getString("openid");
        // 3.拉取用户信息(需scope为 snsapi_userinfo)
        String userInfoUrl = weiXinUtils.getUserInfo(accessToken, openid);
        JSONObject userInfoResult = HttpClientUtils.httpGet(userInfoUrl);
        System.out.println("userInfoResult:" + userInfoResult);
        request.setAttribute("nickname", userInfoResult.getString("nickname"));
        request.setAttribute("city", userInfoResult.getString("city"));
        request.setAttribute("headimgurl", userInfoResult.getString("headimgurl"));
        return "info";
    }

}
