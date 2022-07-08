package com.mall.query;

import com.mall.model.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderMasterQuery extends AbstractQuery {

    private Long id;
    /**
     * 姓名
     */
    private String receiver;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 地址
     */
    private String address;

}

