package com.mall.service;

import com.mall.model.CategoryDTO;
import com.mall.query.CategoryQuery;
import com.mall.status.CategoryStatusUpdater;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface CategoryService {

    public List<CategoryDTO> select(CategoryQuery categoryQuery);

    public Integer insert(CategoryDTO categoryDTO) throws IOException;

    public Integer update(CategoryDTO categoryDTO);

    public Integer updateStatus(CategoryStatusUpdater categoryStatusUpdater);

}
