package com.example.dianping.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * User
 *
 * @author xuyanpeng01 on 2022/4/16
 */
@Data
@Builder
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String telephone;

    private String password;

    private String nickName;

    /**
     * 0 : not set yet
     * 1 : man
     * 2 : woman
     */
    private Integer gender;

    private Date createTime;

    private Date updateTime;
}
