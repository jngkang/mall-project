package com.mall.service.impl;

import com.mall.mapper.GoodsMapper;
import com.mall.model.Goods;
import com.mall.model.query.GoodsQuery;
import com.mall.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> select(GoodsQuery goodsQuery) {
        return goodsMapper.select(goodsQuery);
    }

    @Override
    public Integer insert(Goods goods) {
        return goodsMapper.insert(goods);
    }

    @Override
    public Integer update(Goods goods) {
        return goodsMapper.update(goods);
    }
}
