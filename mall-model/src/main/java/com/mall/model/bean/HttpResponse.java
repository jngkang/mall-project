package com.mall.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author JngKang
 * @date 2022-06-09 16:14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HttpResponse<T> implements Serializable {

    /**
     * 返回码，0表示正常，非0表示异常。返回码值的含义由公司内部定义
     */
    private int code;
    /**
     * 返回信息，一般用于返回错误信息
     */
    private String message;
    /**
     * 结果
     */
    private T data;
}