package com.example.dianping.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * Category
 *
 * @author xuyanpeng01 on 2022/4/20
 */
@Data
@Builder
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String iconUrl;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

}
