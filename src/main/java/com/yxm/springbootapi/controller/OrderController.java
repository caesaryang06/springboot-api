
package com.yxm.springbootapi.controller;

import javax.servlet.http.HttpServletRequest;

import com.yxm.springbootapi.annotation.ExtApiIdempotent;
import com.yxm.springbootapi.entity.OrderEntity;
import com.yxm.springbootapi.mapper.OrderMapper;
import com.yxm.springbootapi.utils.ConstantUtils;
import com.yxm.springbootapi.utils.RedisToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class OrderController {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private RedisToken redisTokenUtils;

	// 从redis中获取Token
	@RequestMapping("/redisToken")
	public String RedisToken() {
		return redisTokenUtils.getToken();
	}

	// 验证Token
	@ExtApiIdempotent(type = ConstantUtils.EXTAPIHEAD)
	@RequestMapping(value = "/addOrderExtApiIdempotent", produces = "application/json; charset=utf-8")
	public String addOrderExtApiIdempotent(@RequestBody OrderEntity orderEntity, HttpServletRequest request) {
		int result = orderMapper.addOrder(orderEntity);
		return result > 0 ? "添加成功" : "添加失败" + "";
	}
}
