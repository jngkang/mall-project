package com.mall.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.entity.Category;
import com.mall.globel.Const;
import com.mall.mapper.CategoryMapper;
import com.mall.model.CategoryDTO;
import com.mall.query.CategoryQuery;
import com.mall.service.CategoryService;
import com.mall.status.CategoryStatusUpdater;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<CategoryDTO> select(CategoryQuery categoryQuery) {
        List<CategoryDTO> res = new ArrayList<>();
        List<Category> lists = categoryMapper.select(categoryQuery);
        for (Category list : lists) {
            CategoryDTO temp = new CategoryDTO();
            temp.setId(list.getId());
            temp.setName(list.getName());
            temp.setImg(list.getImg());
            temp.setPriority(list.getPriority());
            temp.setPid(list.getPid());
            temp.setStatus(list.getStatus());
            temp.setUpdateBy(list.getUpdateBy());
            temp.setUpdateTime(list.getUpdateTime());
            res.add(temp);
        }
        return res;
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
                .status(categoryDTO.getStatus())
                .build();
        category.setImg(uploadImg(categoryDTO));
        category.setUpdateBy(CurrentThreadLocal.get().getUsername());
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.insert(category);
    }

    @Override
    public Integer update(CategoryDTO categoryDTO) {
        if (ObjectUtil.isEmpty(categoryDTO.getStatus())) {
            categoryDTO.setStatus(1);
        }
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .priority(categoryDTO.getPriority())
                .pid(categoryDTO.getPid())
                .status(categoryDTO.getStatus())
                .build();
        category.setImg(uploadImg(categoryDTO));
        category.setUpdateBy(CurrentThreadLocal.get().getUsername());
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.update(category);
    }

    @Override
    public Integer updateStatus(CategoryStatusUpdater categoryStatusUpdater) {
        return categoryMapper.updateStatus(categoryStatusUpdater);
    }

    private String uploadImg(CategoryDTO categoryDTO) {
        if (categoryDTO.getImg().indexOf("base64") > 0) {
            String[] dataArray = StrUtil.splitToArray(categoryDTO.getImg(), "base64,");
            byte[] bytes = Base64.decode(dataArray[1]);
            String imgName = System.currentTimeMillis() + "_" + categoryDTO.getImgName();
            FileUtil.writeBytes(bytes, Const.IMG_SERVICE_PATH + imgName);
            String img = StrUtil.format(Const.IMG_SERVICE + imgName);
            return img;
        }
        return categoryDTO.getImg();
    }
}
