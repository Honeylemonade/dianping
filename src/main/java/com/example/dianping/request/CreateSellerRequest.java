package com.example.dianping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * CreateSellerRequest
 *
 * @author xuyanpeng01 on 2022/4/19
 */
@Data
public class CreateSellerRequest {

    @NotBlank
    private String name;
}
