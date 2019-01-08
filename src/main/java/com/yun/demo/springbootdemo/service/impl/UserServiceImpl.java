package com.yun.demo.springbootdemo.service.impl;

import com.yun.demo.springbootdemo.dao.UserDao;
import com.yun.demo.springbootdemo.entity.UserEntity;
import com.yun.demo.springbootdemo.pojo.PagePojo;
import com.yun.demo.springbootdemo.pojo.PaginationPojo;
import com.yun.demo.springbootdemo.pojo.UserQueryPojo;
import com.yun.demo.springbootdemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public void saveUser(UserEntity user) {
        userDao.saveUser(user);
    }

    @Override
    public PaginationPojo findUsers(UserQueryPojo condition, PagePojo page) {
        return userDao.findUsers(condition, page);
    }

    @Override
    public void updateUser(UserEntity user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(String id) {
        userDao.deleteUserById(id);
    }

}
