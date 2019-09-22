package com.yxm.springbootapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CAESAR
 * @Classname ExtApiIdempotent
 * @Description 实现api接口幂等注解  支持网络延迟  表单重复提交
 * @Date 2019-09-21 23:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtApiIdempotent {

    String type();
}
