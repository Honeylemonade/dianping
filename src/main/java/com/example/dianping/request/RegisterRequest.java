package com.example.dianping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * RegisterRequest
 *
 * @author xuyanpeng01 on 2022/4/16
 */
@Data
public class RegisterRequest {

    @NotBlank
    private String telephone;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @NotNull
    private Integer gender;
}
