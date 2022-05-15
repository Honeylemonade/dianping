package com.example.dianping.controller;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dianping.common.CommonRs;
import com.example.dianping.request.AdminLoginRequest;

/**
 * AdminController
 *
 * @author xuyanpeng01 on 2022/4/17
 */
@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    @Value("${admin.user-name}")
    private String adminUserName;

    @Value("${admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    public CommonRs login(@Valid @RequestBody AdminLoginRequest adminLoginRequest) {
        try {
            if (adminUserName.equals(adminLoginRequest.getUserName())
                && adminPassword.equals(adminLoginRequest.getPassword())) {
                return CommonRs.success(null, "登录成功");
            }
            return CommonRs.fail(null, "登录失败");
        } catch (Exception e) {
            return CommonRs.fail(null, "登录失败");
        }

    }
}
