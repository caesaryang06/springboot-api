package com.yxm.springbootapi.config;

import com.yxm.springbootapi.interceptor.AccessTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author CAESAR
 * @Classname WebAppConfig
 * @Description TODO
 * @Date 2019-09-22 17:05
 */
@Configuration
public class WebAppConfig {

    @Autowired
    private AccessTokenInterceptor accessTokenInterceptor;


    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(accessTokenInterceptor).addPathPatterns("/openApi/*");
            }
        };
    }


}
