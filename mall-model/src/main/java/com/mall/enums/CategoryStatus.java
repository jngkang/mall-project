package com.mall.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author JngKang
 * @date 2022-07-01 16:59
 */
public enum CategoryStatus {
    PUTON(1, "上架"),
    PUTOFF(0, "下架");

    private Integer code;
    private String name;

    CategoryStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static CategoryStatus findByCode(Integer code) {
        Optional<CategoryStatus> first = Arrays.stream(CategoryStatus.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst();
        return first.orElse(null);
    }

}
