package com.example.dianping.service;

import com.example.dianping.model.User;

/**
 * UserService
 *
 * @author xuyanpeng01 on 2022/4/16
 */
public interface UserService {

    User getUser(Integer id);

    User register(User user);

    User login(String telephone, String password);
}
