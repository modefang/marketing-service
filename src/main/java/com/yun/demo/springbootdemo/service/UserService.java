package com.yun.demo.springbootdemo.service;

import com.yun.demo.springbootdemo.entity.UserEntity;
import com.yun.demo.springbootdemo.pojo.PagePojo;
import com.yun.demo.springbootdemo.pojo.PaginationPojo;
import com.yun.demo.springbootdemo.pojo.UserQueryPojo;

public interface UserService {

    /**
     * 根据用户名获取用户数据
     * @param username 用户名
     */
    UserEntity loadUserByUsername(String username);

    /**
     * 新增用户
     * @param user 用户
     */
    void saveUser(UserEntity user);

    /**
     * 分页展示所有用户
     * @param condition 查询参数
     * @param page 分页参数
     */
    PaginationPojo findUsers(UserQueryPojo condition, PagePojo page);

    /**
     * 更新用户数据
     * @param user 用户
     */
    void updateUser(UserEntity user);

    /**
     * 根据id删除用户
     * @param id 用户id
     */
    void deleteUserById(String id);

}
