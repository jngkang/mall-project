package com.mall.config;

import com.mall.intercepter.MapperInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JngKang
 * @date 2022-06-09 15:03
 */
@Configuration
public class AopConfig {

    @Value("${pointcut.property}")
    private String pointcut;

    @Bean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(new MapperInterceptor());
        return advisor;
    }

}