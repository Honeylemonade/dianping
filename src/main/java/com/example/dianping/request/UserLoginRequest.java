package com.example.dianping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * LoginRequest
 *
 * @author xuyanpeng01 on 2022/4/17
 */
@Data
public class UserLoginRequest {

    @NotBlank
    private String telephone;

    @NotBlank
    private String password;
}
