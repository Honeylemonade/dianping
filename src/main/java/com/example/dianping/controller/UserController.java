package com.example.dianping.controller;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dianping.common.CommonRs;
import com.example.dianping.model.User;
import com.example.dianping.request.UserLoginRequest;
import com.example.dianping.request.RegisterRequest;
import com.example.dianping.service.UserService;

/**
 * UserController
 *
 * @author xuyanpeng01 on 2022/4/16
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public CommonRs register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = User.builder().telephone(registerRequest.getTelephone())
            .password(registerRequest.getPassword())
            .nickName(registerRequest.getNickName())
            .gender(registerRequest.getGender())
            .build();

        User register = userService.register(user);
        return CommonRs.success(register);
    }

    @PostMapping("/login")
    public CommonRs login(@Valid @RequestBody UserLoginRequest loginRequest) {
        User user = userService.login(loginRequest.getTelephone(), loginRequest.getPassword());

        if (Objects.isNull(user)) {
            return CommonRs.fail(null, "登录失败");
        }
        return CommonRs.success(user);
    }

    @GetMapping("/{id}")
    public CommonRs getUser(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        if (Objects.isNull(user)) {
            return CommonRs.fail(null, "This user doesn't exist.");
        }
        return CommonRs.success(user);
    }
}
