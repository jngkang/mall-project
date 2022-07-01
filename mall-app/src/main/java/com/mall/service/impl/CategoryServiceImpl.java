package com.mall.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.globel.Const;
import com.mall.mapper.CategoryMapper;
import com.mall.model.Category;
import com.mall.model.dto.CategoryDTO;
import com.mall.model.query.CategoryQuery;
import com.mall.service.CategoryService;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> select(CategoryQuery categoryQuery) {
        return categoryMapper.select(categoryQuery);
    }

    @Override
    public Integer insert(CategoryDTO categoryDTO) throws IOException {
        if (ObjectUtil.isEmpty(categoryDTO.getStatus())) {
            categoryDTO.setStatus(1);
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .priority(categoryDTO.getPriority())
                .pid(categoryDTO.getPid())
                .status(ObjectUtil.isEmpty(categoryDTO.getStatus()) ? 1 : categoryDTO.getStatus())
                .build();
        String[] dataArray = StrUtil.splitToArray(categoryDTO.getImg(), "base64,");
        byte[] bytes = Base64.decode(dataArray[1]);
        String imgName = System.currentTimeMillis() + "_" + categoryDTO.getImgName();
        FileUtil.writeBytes(bytes, Const.IMG_SERVICE_PATH + imgName);
        String img = StrUtil.format(Const.IMG_SERVICE + imgName);
        category.setImg(img);
        category.setUpdateBy(CurrentThreadLocal.get().getUsername());
        return categoryMapper.insert(category);
    }

    @Override
    public Integer update(Category category) {
        return categoryMapper.update(category);
    }
}
