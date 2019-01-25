package com.yun.demo.springbootdemo.dao.impl;

import com.yun.demo.springbootdemo.dao.UserDao;
import com.yun.demo.springbootdemo.entity.UserEntity;
import com.yun.demo.springbootdemo.pojo.PagePojo;
import com.yun.demo.springbootdemo.pojo.PaginationPojo;
import com.yun.demo.springbootdemo.pojo.UserQueryPojo;
import com.yun.demo.springbootdemo.util.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public UserEntity loadUserByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return this.mongoTemplate.findOne(query, UserEntity.class);
    }

    @Override
    public void saveUser(UserEntity user) {
        this.mongoTemplate.save(user);
    }

    @Override
    public PaginationPojo findUsers(UserQueryPojo condition, PagePojo page) {
        Query query = new Query();
        if(StringUtils.isNotEmpty(condition.getId())) {
            query.addCriteria(Criteria.where("id").is(condition.getId()));
        }
        if(StringUtils.isNotEmpty(condition.getUsername())) {
            query.addCriteria(Criteria.where("username").regex(".*" + condition.getUsername() + ".*"));
        }
        if(StringUtils.isNotEmpty(condition.getUpdateTimeStart())) {
            query.addCriteria(Criteria.where("updateTime").gte(condition.getUpdateTimeStart()));
        }
        if(StringUtils.isNotEmpty(condition.getUpdateTimeEnd())) {
            query.addCriteria(Criteria.where("updateTime").lte(condition.getUpdateTimeEnd()));
        }
        if(StringUtils.isNotEmpty(condition.getCreateTimeStart())) {
            query.addCriteria(Criteria.where("createTime").gte(condition.getCreateTimeStart()));
        }
        if(StringUtils.isNotEmpty(condition.getCreateTimeEnd())) {
            query.addCriteria(Criteria.where("createTime").lte(condition.getCreateTimeEnd()));
        }

        Long total = this.mongoTemplate.count(query, UserEntity.class);
        Query listQuery = query.with(new Sort(Sort.Direction.DESC, "createTime")).skip(page.getSkip()).limit(page.getSize());
        List<UserEntity> list = this.mongoTemplate.find(listQuery, UserEntity.class);
        return new PaginationPojo(list, total);
    }

    @Override
    public void updateUser(UserEntity user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("username", user.getUsername()).set("password", user.getPassword()).set("updateTime", new Date());
        this.mongoTemplate.updateFirst(query, update, UserEntity.class);
    }

    @Override
    public void deleteUserById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        this.mongoTemplate.remove(query, UserEntity.class);
    }

}
