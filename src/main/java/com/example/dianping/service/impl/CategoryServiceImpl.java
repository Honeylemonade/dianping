package com.example.dianping.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dianping.mapper.CategoryMapper;
import com.example.dianping.model.Category;
import com.example.dianping.service.CategoryService;

/**
 * CategoryServiceImpl
 *
 * @author xuyanpeng01 on 2022/4/20
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public Category create(Category category) {
        categoryMapper.insert(category);
        return get(category.getId());
    }

    @Override
    public Category get(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
            .orderByDesc(Category::getSort)
            .orderByAsc(Category::getId));
    }
}
