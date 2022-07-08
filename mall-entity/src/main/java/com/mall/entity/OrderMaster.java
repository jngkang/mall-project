package com.mall.entity;

import com.mall.annotation.PK;
import com.mall.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_order_master")
public class OrderMaster implements Serializable {
    /**
     * 编号
     */
    @PK(autoIncrement = true)
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

