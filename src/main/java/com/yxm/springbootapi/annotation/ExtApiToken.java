package com.yxm.springbootapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CAESAR
 * @Classname ExtApiToken
 * @Description 生成token注解  添加到哪个方法上  就会给请求这个服务的请求添加token
 * @Date 2019-09-22 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtApiToken {
}
