package com.example.dianping.service;

import java.util.List;

import com.example.dianping.model.Category;

/**
 * CategoryService
 */
public interface CategoryService {

    Category create(Category category);

    Category get(Integer id);

    List<Category> selectAll();
}
