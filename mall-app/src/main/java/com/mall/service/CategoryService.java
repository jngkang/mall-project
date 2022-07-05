package com.mall.service;

import com.mall.model.status.CategoryStatusUpdater;
import com.mall.model.dto.CategoryDTO;
import com.mall.model.query.CategoryQuery;
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
