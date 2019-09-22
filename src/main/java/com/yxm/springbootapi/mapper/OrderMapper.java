/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.yxm.springbootapi.mapper;

import com.yxm.springbootapi.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;


public interface OrderMapper {
	/**
	 * 添加订单
	 * @param orderEntity
	 * @return
	 */
	@Insert("insert order_info values (null,#{orderName},#{orderDes})")
	int addOrder(OrderEntity orderEntity);
}
