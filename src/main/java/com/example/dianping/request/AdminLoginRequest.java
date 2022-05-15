package com.example.dianping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * AdminLoginRequest
 *
 * @author xuyanpeng01 on 2022/4/17
 */
@Data
public class AdminLoginRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
