package com.mall.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mall.annotation.NoAuthorization;
import com.mall.enums.CategoryStatus;
import com.mall.model.Category;
import com.mall.model.CategoryStatusUpdater;
import com.mall.model.dto.CategoryDTO;
import com.mall.model.query.CategoryQuery;
import com.mall.model.query.dto.CategoryQueryDTO;
import com.mall.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-30 08:49
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @NoAuthorization
    @PostMapping("/page")
    public List select(@RequestBody CategoryQueryDTO categoryQueryDTO) {
        CategoryQuery query = new CategoryQuery();
        query.setId(categoryQueryDTO.getId());
        query.setName(categoryQueryDTO.getName());
        query.setPid(categoryQueryDTO.getPid());
        query.setStatus(categoryQueryDTO.getStatus());
        query.setUpdateBy(categoryQueryDTO.getUpdateBy());
        query.setUpdateTime(categoryQueryDTO.getUpdateTime());

        // 创建一个集合
        List<Category> categoryList = categoryService.select(query);

        if (categoryQueryDTO.getIsTree() == 1) {
            //配置
            TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
            // 最大递归深度
            treeNodeConfig.setDeep(categoryQueryDTO.getDeep());
            //转换器
            String pid = ObjectUtil.isEmpty(categoryQueryDTO.getPid()) ? "0" : categoryQueryDTO.getPid().toString();
            List<Tree<String>> treeNodes = TreeUtil.build(categoryList, pid, treeNodeConfig,
                    (category, tree) -> {
                        tree.setId(category.getId().toString());
                        tree.setParentId(category.getPid().toString());
                        tree.setWeight(category.getPriority());
                        tree.setName(category.getName());
                        // 扩展属性 ...
                        tree.putExtra("value", category.getId().toString());
                        tree.putExtra("label", category.getName());

                        tree.putExtra("img", category.getImg());
                        tree.putExtra("statusX", CategoryStatus.findByCode(category.getStatus()).getName());
                        tree.putExtra("updateBy", category.getUpdateBy());
                        tree.putExtra("updateTime", category.getUpdateTime());
                        tree.putExtra("priority", category.getPriority());
                    });
            return treeNodes;
        } else {
            return categoryList;
        }
    }

    @PostMapping("/add")
    public Integer insert(@RequestBody CategoryDTO categoryDTO) throws IOException {
        return categoryService.insert(categoryDTO);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.update(categoryDTO);
    }

    @PostMapping("/status/update")
    public Integer updateStatus(@RequestBody CategoryStatusUpdater categoryStatusUpdater) {
        return categoryService.updateStatus(categoryStatusUpdater);
    }
}
