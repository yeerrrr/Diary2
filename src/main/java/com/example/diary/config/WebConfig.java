package com.example.diary.config;

import com.example.diary.HandlerInterceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration   //为了让拦截器生效
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {  //添加拦截器
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/login/**"); //在搜索前拦截所有请求
    }
}
