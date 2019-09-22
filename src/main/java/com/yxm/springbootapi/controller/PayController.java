package com.yxm.springbootapi.controller;

import com.yxm.springbootapi.base.BaseApiService;
import com.yxm.springbootapi.base.ResponseBase;
import com.yxm.springbootapi.utils.BaseRedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author CAESAR
 * @Classname PayController
 * @Description 基于令牌方式实现接口参数安全传输
 *
 * @Date 2019-09-22 22:40
 */
@RestController
public class PayController extends BaseApiService {

    @Autowired
    private BaseRedisService baseRedisService;


    /**
     * 1.先获取参数接口 返回令牌  (不是前端  是服务端调用它)
     *  2.使用令牌传递参数
     * @param userId
     * @param money
     * @return
     */
    @RequestMapping("/getPayToken")
    public String getPayToken(String userId,Long money){

        // 生成令牌
        String token = UUID.randomUUID().toString();
        baseRedisService.setString(token,userId+"-----"+money,60*30L);
        return token;
    }

    @RequestMapping("/pay")
    public ResponseBase pay(String payToken){

        if (StringUtils.isEmpty(payToken)) {
            return setResultError("token 不能为空");
        }
        String result = (String) baseRedisService.getString(payToken);
        if (StringUtils.isEmpty(result)) {
            return setResultError("参数不能为空");
        }
        //操作数据库

        return setResultSuccess(result);
    }

}
