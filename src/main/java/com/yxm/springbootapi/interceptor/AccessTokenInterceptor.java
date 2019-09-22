package com.yxm.springbootapi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yxm.springbootapi.base.BaseApiService;
import com.yxm.springbootapi.entity.AppEntity;
import com.yxm.springbootapi.mapper.AppMapper;
import com.yxm.springbootapi.utils.BaseRedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author CAESAR
 * @Classname AccessTokenInterceptor
 * @Description 拦截器
 * @Date 2019-09-22 16:49
 */
@Component
public class AccessTokenInterceptor extends BaseApiService implements HandlerInterceptor {

    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
    private AppMapper appMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("======================开始进入请求拦截==========================");
        String accessToken = request.getParameter("accessToken");
        // 1. 获取accesstoken
        if (StringUtils.isEmpty(accessToken)) {
             resultError("accessToken is null",response);
             return false;
        }
        // 2. 使用accesstoken 查询缓存
        String appId = (String) baseRedisService.getString(accessToken);
        // 3. 不存在  无效token
        if (StringUtils.isEmpty(appId)) {
            resultError("accessToken Invalid",response);
            return false;
        }
        // 4. 存在   使用appid查询数据库  获取到记录  判断是否可用
        AppEntity appEntity = appMapper.findAppId(appId);
        if (appEntity == null) {
            resultError("not found app info",response);
            return false;
        }
        // 5. 不可用   提示暂时不开放  权限不足
        if (appEntity.getIsFlag() == 1) {
            resultError("permission denied",response);
            return  false;
        }

       // 走正常业务路偶记
        return true;
    }

    // 返回错误提示
    public void resultError(String errorMsg, HttpServletResponse httpServletResponse) throws IOException {
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(JSONObject.toJSONString(setResultError(errorMsg)));
    }
}
