package com.example.dianping.model;

import lombok.Data;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * Position
 *
 * @author xuyanpeng01 on 2022/4/21
 */
@Data
public class Position {

    private String address;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
