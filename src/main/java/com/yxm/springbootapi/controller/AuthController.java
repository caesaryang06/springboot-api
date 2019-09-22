package com.yxm.springbootapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxm.springbootapi.base.BaseApiService;
import com.yxm.springbootapi.base.ResponseBase;
import com.yxm.springbootapi.entity.AppEntity;
import com.yxm.springbootapi.mapper.AppMapper;
import com.yxm.springbootapi.utils.BaseRedisService;
import com.yxm.springbootapi.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CAESAR
 * @Classname AuthController
 * @Description TODO
 * @Date 2019-09-22 15:20
 */
@RestController
@RequestMapping("auth")
public class AuthController extends BaseApiService {

    @Autowired
    private BaseRedisService redisService;

    @Autowired
    private AppMapper appMapper;

    @GetMapping("/getAccessToken")
    public ResponseBase getAccessToken(AppEntity appEntity){
        // 使用appid 和 appsecret查询信息
        AppEntity app = appMapper.findApp(appEntity);
        // 验证是否可用
        if (app == null) {
            return setResultError("没有对应机构信息");
        }
        if (app.getIsFlag() == 1) {
            return setResultError("暂时对该机构不开放，如有疑问请联系客服!");
        }
        // 使用appid 和 appsecret 生成对应的accessToken  存入缓存
        String accessToken = TokenUtils.getAccessToken();
        redisService.setString(accessToken,app.getAppId(),60*60*2L);
        // 删除之前的token
        String oldAccessToken = app.getAccessToken();
        if (StringUtils.isNotEmpty(oldAccessToken)) {
            redisService.delKey(oldAccessToken);
        }
        // 更新到数据库中
        appMapper.updateAccessToken(accessToken,app.getAppId());
        // 4.返回最新的token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accessToken",accessToken);
        return setResultSuccessData(jsonObject);
    }


}
