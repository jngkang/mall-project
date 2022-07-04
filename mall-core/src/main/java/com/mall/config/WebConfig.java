package com.mall.config;

import com.mall.intercepter.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${token.key}")
    private String tokenKey;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor handlerInterceptor = new TokenInterceptor(tokenKey);
        registry.addInterceptor(handlerInterceptor)
                // 拦截请求
                .addPathPatterns("/api/**");
        //.excludePathPatterns("/fun"); // 放行指定接口
    }
}