package com.yxm.springbootapi.aop;

import com.yxm.springbootapi.annotation.ExtApiIdempotent;
import com.yxm.springbootapi.utils.ConstantUtils;
import com.yxm.springbootapi.utils.RedisToken;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author CAESAR
 * @Classname ExtApiIdmpotentAop
 * @Description TODO
 * @Date 2019-09-21 23:13
 */
@Aspect
@Component
public class ExtApiIdmpotentAop {

    @Autowired
    private RedisToken redisToken;

    /**
     * 定义切入点  拦截 com.yxm.springbootapi.controller
     */
    @Pointcut("execution(public * com.yxm.springbootapi.controller.*.*(..))")
    public void alAop(){}


    /**
     * 环绕通知
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("alAop()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        // 获取拦截方法上注解
        ExtApiIdempotent annotation = getAnnotation(point);
        if (annotation != null) {
            String type = annotation.type();
            HttpServletRequest request = getRequest();
            String token;
            if (ConstantUtils.EXTAPIHEAD.equals(type)) {
                 token = request.getHeader("token");
            }else{
                token = request.getParameter("token");
            }

            if (StringUtils.isEmpty(token)) {
                return "参数错误";
            }

            // 接口获取对象的令牌 如果能获取到 （就删除该令牌）
            if (!redisToken.findToken(token)) {
                response("请勿重复提交");
                return null;
            }
        }

        // 调用目标代理对象方法
        return point.proceed();
    }


    /**
     * 校验失败  返回提示信息
     */
    private void response(String message) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            writer.println(message);
        } catch (Exception e) {

        } finally {
            writer.close();
        }
    }



    /**
     * 获取当前请求的url
     * @return
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }


    /**
     * 获取方法上的注解
     * @param point
     * @return
     */
    private ExtApiIdempotent getAnnotation(ProceedingJoinPoint point) throws NoSuchMethodException {
        // 1.1获取方法名称
        String methodName = point.getSignature().getName();
        // 1.2获取目标对象
        Class<?> classTarget = point.getTarget().getClass();
        //  1.3获取目标对象类型
        Class<?>[] par = ((MethodSignature) point.getSignature()).getParameterTypes();
        // 1.4获取目标对象方法
        Method objMethod = classTarget.getMethod(methodName, par);
        // 1.5获取该方法上的事务注解
        ExtApiIdempotent annotation = objMethod.getDeclaredAnnotation(ExtApiIdempotent.class);
        return annotation;

    }

}
