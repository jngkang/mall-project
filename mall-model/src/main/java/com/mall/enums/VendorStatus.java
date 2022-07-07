package com.mall.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author JngKang
 * @date 2022-07-01 16:59
 */
public enum VendorStatus {
    PUTON(1, "启用"),
    PUTOFF(0, "禁用");

    private Integer code;
    private String name;

    VendorStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static VendorStatus findByCode(Integer code) {
        Optional<VendorStatus> first = Arrays.stream(VendorStatus.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst();
        return first.orElse(null);
    }

}
