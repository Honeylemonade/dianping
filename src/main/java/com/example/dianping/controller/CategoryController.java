package com.example.dianping.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dianping.common.CommonRs;
import com.example.dianping.model.Category;
import com.example.dianping.request.CreateCategoryRequest;
import com.example.dianping.service.CategoryService;

/**
 * CategoryController
 *
 * @author xuyanpeng01 on 2022/4/20
 */
@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public CommonRs getAll() {
        return CommonRs.success(categoryService.selectAll());
    }

    @PostMapping
    public CommonRs create(@RequestBody CreateCategoryRequest createCategoryRequest) {
        try {
            Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .iconUrl(createCategoryRequest.getIconUrl())
                .sort(createCategoryRequest.getSort())
                .build();

            Category result = categoryService.create(category);

            return CommonRs.success(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CommonRs.fail(null, "创建品类失败");
        }
    }

}
