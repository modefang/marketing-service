package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.entity.UserEntity;
import com.yun.demo.springbootdemo.pojo.PagePojo;
import com.yun.demo.springbootdemo.pojo.PaginationPojo;
import com.yun.demo.springbootdemo.pojo.ResponsePojo;
import com.yun.demo.springbootdemo.pojo.UserQueryPojo;
import com.yun.demo.springbootdemo.service.UserService;
import com.yun.demo.springbootdemo.util.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Resource
    private UserService userService;

    @RequestMapping("/saveUser")
    public ResponsePojo saveUser(UserEntity user) {
        if(StringUtils.isEmpty(user.getUsername())) {
            return new ResponsePojo(ResponseEnum.ERROR_PARAMETERS_IS_NULL, user);
        }
        user.setId(UUID.randomUUID().toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        userService.saveUser(user);
        return new ResponsePojo(ResponseEnum.SUCCESS, user);
    }

    @RequestMapping("/findUsers")
    public ResponsePojo findUsers(UserQueryPojo userQuery, Integer pageIndex) {
        PagePojo page = new PagePojo(pageIndex);
        PaginationPojo pagination = userService.findUsers(userQuery, page);
        return new ResponsePojo(ResponseEnum.SUCCESS, pagination);
    }

    @RequestMapping("/updateUser")
    public ResponsePojo updateUser(UserEntity user) {
        if(StringUtils.isEmpty(user.getId(), user.getUsername())) {
            return new ResponsePojo(ResponseEnum.ERROR_PARAMETERS_IS_NULL, user);
        }
        userService.updateUser(user);
        return new ResponsePojo(ResponseEnum.SUCCESS, user);
    }

    @RequestMapping("/deleteUser")
    public ResponsePojo deleteUser(String id) {
        if(StringUtils.isEmpty(id)) {
            return new ResponsePojo(ResponseEnum.ERROR_PARAMETERS_IS_NULL, id);
        }
        userService.deleteUserById(id);
        return new ResponsePojo(ResponseEnum.SUCCESS, id);
    }

}
