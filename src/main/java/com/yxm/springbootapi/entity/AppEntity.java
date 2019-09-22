package com.yxm.springbootapi.entity;

import lombok.Data;

/**
 * @author CAESAR
 * @Classname AppEntity
 * @Description TODO
 * @Date 2019-09-22 15:04
 */
@Data
public class AppEntity {

    private  Long id;

    /**
     * 表示机构名称
     */
    private  String appName;


    private  String appId;


    private  String appSecret;


    private  Integer isFlag;


    private  String accessToken;

}
