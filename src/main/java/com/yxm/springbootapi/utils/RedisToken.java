package com.yxm.springbootapi.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RedisToken {
	private long timeout = 60 * 60;
	@Autowired
	private BaseRedisService baseRedisService;

	/**
	 * 将token存入在redis
	 * @return
	 */
	public String getToken() {
		String token = "token" + System.currentTimeMillis();
		baseRedisService.setString(token, token, timeout);
		return token;
	}

//	如何使用token解决幂等性问题
//	步骤：
//	1.在调用接口之前生成对应的令牌（token）存放在redis中
//	2.调用接口的时候，将该令牌放入请求头中
//	3.接口获取对象的令牌 如果能获取到 （就删除该令牌）  就执行相应的业务逻辑




	public synchronized boolean findToken(String tokenKey) {
		String token = (String) baseRedisService.getString(tokenKey);
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		// token 获取成功后 删除对应tokenMapstoken
		baseRedisService.delKey(token);
		return true;
	}





}
