package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 入库商品表(TbBillItem)实体类
 *
 * @author makejava
 * @since 2022-07-09 08:27:10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BillItemDTO implements Serializable {

    private Integer id;

    private Integer qty;

}

