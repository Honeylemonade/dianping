package com.example.dianping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * Shop
 *
 * @author xuyanpeng01 on 2022/4/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String name;

    private Double remarkScore;

    private Integer pricePerMan;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @TableField(exist = false)
    private BigDecimal distance;

    private Integer categoryId;

    @TableField(exist = false)
    private Category category;

    private String tags;

    private String startTime;

    private String endTime;

    private String address;


    private Integer sellerId;

    @TableField(exist = false)
    private Seller seller;

    private String iconUrl;

}
