package com.mall.enums;

import java.util.Arrays;
import java.util.Optional;

public enum BillType {
    BILL_IN(1, "入库"),
    BILL_OUT(2, "出库"),
    BILL_C(3, "C端下单"),
    BILL_B(4, "B端下单");

    private Integer code;
    private String name;

    BillType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BillType findByCode(Integer code) {
        Optional<BillType> first = Arrays.stream(BillType.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst();
        return first.orElse(null);
    }
}