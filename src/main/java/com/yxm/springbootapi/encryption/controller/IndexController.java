package com.yxm.springbootapi.encryption.controller;

import com.yxm.springbootapi.base.BaseApiService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CAESAR
 * @Classname IndexController
 * @Description http协议特殊字符处理
 * @Date 2019-09-22 20:30
 */
@RestController
public class IndexController extends BaseApiService {


    /**
     *测试案例    请求url的参数传 1+1  http://localhost:8080/testUrl?userName=1+1
     * @param userName
     * @return
     */
    @RequestMapping("/testUrl")
    public Object indexPage(String userName){

        System.out.println(userName);

        return setResultSuccess(userName);
    }

}
