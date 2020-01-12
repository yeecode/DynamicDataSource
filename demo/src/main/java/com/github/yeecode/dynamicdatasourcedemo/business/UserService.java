package com.github.yeecode.dynamicdatasourcedemo.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public String select() {
        return userMapper.select();
    }
}
