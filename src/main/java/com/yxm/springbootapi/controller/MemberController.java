package com.yxm.springbootapi.controller;

import com.yxm.springbootapi.base.BaseApiService;
import com.yxm.springbootapi.base.ResponseBase;
import com.yxm.springbootapi.entity.AppEntity;
import com.yxm.springbootapi.mapper.AppMapper;
import com.yxm.springbootapi.utils.BaseRedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CAESAR
 * @Classname MemberController
 * @Description
 * @Date 2019-09-22 15:59
 */
@RestController
@RequestMapping("/openApi/member")
public class MemberController extends BaseApiService {

    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
    private AppMapper appMapper;

    @GetMapping("/user")
    public ResponseBase getUser(String accessToken){
        return setResultSuccess("success  已查询结果");
    }

}
