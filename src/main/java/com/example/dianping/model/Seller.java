package com.example.dianping.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @author xuyanpeng01 on 2022/4/19
 */
@Data
@Builder
public class Seller {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Date createTime;

    private Date updateTime;

    /**
     * 0.0 ~ 5.0
     */
    private double remarkScore;

    /**
     * 0 表示启用
     * 1 表示禁用
     */
    private Integer disabledFlag;
}
