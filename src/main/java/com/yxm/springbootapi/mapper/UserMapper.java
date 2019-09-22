/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.yxm.springbootapi.mapper;

import com.yxm.springbootapi.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;



public interface UserMapper {

	@Select(" SELECT  * FROM user_info where userName=#{userName} and password=#{password}")
	public UserEntity login(UserEntity userEntity);

	@Insert("insert user_info values (null,#{userName},#{password})")
	public int insertUser(UserEntity userEntity);
}
