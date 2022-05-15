package com.example.dianping.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dianping.mapper.UserMapper;
import com.example.dianping.model.User;
import com.example.dianping.service.UserService;

/**
 * UserServiceImpl
 *
 * @author xuyanpeng01 on 2022/4/16
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public User getUser(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional
    public User register(User user) {
        // TODO 密码加密
        userMapper.insert(user);
        return getUser(user.getId());
    }

    @Override
    public User login(String telephone, String password) {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>()
            .eq(User::getTelephone, telephone)
            .eq(User::getPassword, password));

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }
}
