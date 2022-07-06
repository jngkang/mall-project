package com.mall.advice;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mall.PageXInfo;
import com.mall.annotation.NoWapper;
import com.mall.model.bean.HttpResponse;
import com.mall.threadlocal.PageXThreadLocal;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JngKang
 * @date 2022-06-09 16:17
 */
@RestControllerAdvice(basePackages = {"com.mall.controller"}) // 绑定指定包下的所有类
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    /**
     * 决定是否拦截
     *
     * @param returnType
     * @param converterType
     * @return boolean 返回true拦截，false不拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 获取所拦截的方法所在的类
        Class<?> declaringClass = returnType.getDeclaringClass();
        // 判断拦截的方法所在的类上是否有@NoWapper注解，若有则不拦截，若没有拦截
        boolean res = AnnotationUtil.hasAnnotation(declaringClass, NoWapper.class);
        return !res;
    }

    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req, Exception ex) {
        HttpResponse<Object> httpResponse = new HttpResponse<>();
        httpResponse.setCode(1);
        httpResponse.setMessage(ex.getMessage());
        return httpResponse;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpResponse<Object> httpResponse = new HttpResponse<>();
        httpResponse.setCode(0);
        // 默认空值时，统一使用Hutool工具提供的统一空值常量
        String message = StrUtil.EMPTY;
        httpResponse.setMessage(message);
        // 从ThreadLocal中获取分页数据，并且进行分页
        PageXInfo pageXInfo = PageXThreadLocal.get();
        if (pageXInfo != null) {
            Dict dic = Dict.create()
                    .set("total", pageXInfo.getTotal())
                    .set("pages", pageXInfo.getPages())
                    .set("items", body);
            httpResponse.setData(dic);
        } else {
            httpResponse.setData(body);
        }
        if (selectedConverterType == StringHttpMessageConverter.class) {
            return JSONUtil.toJsonStr(httpResponse);
        } else {
            return httpResponse;
        }
    }
}