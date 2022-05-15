package com.example.dianping.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xuyanpeng01 on 2022/4/21
 */
@Data
public class CreateShopRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer pricePerMan;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotNull
    private Integer categoryId;

    private String tags;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private String address;

    @NotNull
    private Integer sellerId;

    @NotBlank
    private String iconUrl;
}
