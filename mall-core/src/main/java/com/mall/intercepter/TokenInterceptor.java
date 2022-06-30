package com.mall.intercepter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.mall.annotation.NoAuthorization;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter
public class TokenInterceptor implements HandlerInterceptor {

    private String tokenKey;

    public TokenInterceptor(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoAuthorization noAuthorization = handlerMethod.getMethodAnnotation(NoAuthorization.class);
            // 判断所拦截的接口上是否有@NoAuthorization注解，若存在则直接放行
            if (noAuthorization != null) {
                return true;
            }
            String token = request.getHeader("Authorization");
            if (StrUtil.isNotEmpty(token)) {
                // 验证token是否合法，合法的放行，不合法返回401（无权限）并拦截
                boolean pass = JWTUtil.verify(token, tokenKey.getBytes());
                if (pass) {
                    return true;
                }
            }
            response.setStatus(401);
            return false;
        }
        return true;
    }
}