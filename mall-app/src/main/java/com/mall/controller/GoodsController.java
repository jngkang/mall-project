package com.mall.controller;

import com.mall.model.Goods;
import com.mall.model.dto.GoodsDTO;
import com.mall.model.query.GoodsQuery;
import com.mall.service.GoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-07-02 10:31
 */
@RestController
@RequestMapping("/api")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @PostMapping("/goods/add")
    public String insert(@RequestBody GoodsDTO goodsDTO) {
        Goods goods = Goods.builder()
                .name(goodsDTO.getName())
                .caption(goodsDTO.getCaption())
                .categoryId(goodsDTO.getCategoryId())
                .unit(goodsDTO.getUnit())
                .addr(goodsDTO.getAddr())
                .info(goodsDTO.getInfo())
                .build();
        Integer res = goodsService.insert(goods);
        return res > 0 ? "ok" : "err";
    }

    @PostMapping("/goods/page")
    public List getPage(@RequestBody GoodsQuery goodsQuery) {
        return goodsService.select(goodsQuery);
    }
}
