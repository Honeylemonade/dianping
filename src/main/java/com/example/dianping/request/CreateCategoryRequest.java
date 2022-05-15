package com.example.dianping.request;

import lombok.Data;

/**
 * @author xuyanpeng01 on 2022/4/20
 */
@Data
public class CreateCategoryRequest {

    private String name;

    private String iconUrl;

    private Integer sort;

}
