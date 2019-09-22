
package com.yxm.springbootapi.controller;

import javax.servlet.http.HttpServletRequest;

import com.yxm.springbootapi.annotation.ExtApiIdempotent;
import com.yxm.springbootapi.entity.OrderEntity;
import com.yxm.springbootapi.mapper.OrderMapper;
import com.yxm.springbootapi.utils.ConstantUtils;
import com.yxm.springbootapi.utils.RedisToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class OrderPageController {
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private RedisToken redisTokenUtils;

	@RequestMapping("/indexPage")
	public String indexPage(HttpServletRequest req) {
		req.setAttribute("token",redisTokenUtils.getToken());
		return "indexPage";
	}

	@RequestMapping("/addOrderPage")
	@ExtApiIdempotent(type = ConstantUtils.EXTAPIFROM)
	public String addOrder(OrderEntity orderEntity) {
		int addOrder = orderMapper.addOrder(orderEntity);
		return addOrder > 0 ? "success" : "fail";
	}

}
