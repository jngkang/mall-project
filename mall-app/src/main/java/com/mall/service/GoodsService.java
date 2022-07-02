package com.mall.service;

import com.mall.model.Goods;
import com.mall.model.query.GoodsQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface GoodsService {

    public List<Goods> select(GoodsQuery goodsQuery);

    public Integer insert(Goods goods);

    public Integer update(Goods goods);

}
