package com.yun.demo.springbootdemo.dao.impl;

import com.yun.demo.springbootdemo.dao.UserDao;
import com.yun.demo.springbootdemo.entity.UserEntity;
import com.yun.demo.springbootdemo.pojo.PagePojo;
import com.yun.demo.springbootdemo.pojo.PaginationPojo;
import com.yun.demo.springbootdemo.pojo.UserQueryPojo;
import com.yun.demo.springbootdemo.util.StrUtil;
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
    public void saveUser(UserEntity user) {
        this.mongoTemplate.save(user);
    }

    @Override
    public PaginationPojo findUsers(UserQueryPojo condition, PagePojo page) {
        Query query = new Query();
        if(StrUtil.isNotEmpty(condition.getId())) {
            query.addCriteria(Criteria.where("id").is(condition.getId()));
        }
        if(StrUtil.isNotEmpty(condition.getName())) {
            query.addCriteria(Criteria.where("name").regex(".*" + condition.getName() + ".*"));
        }
        if(StrUtil.isNotEmpty(condition.getUpdateTimeStart())) {
            query.addCriteria(Criteria.where("updateTime").gte(condition.getUpdateTimeStart()));
        }
        if(StrUtil.isNotEmpty(condition.getUpdateTimeEnd())) {
            query.addCriteria(Criteria.where("updateTime").lte(condition.getUpdateTimeEnd()));
        }
        if(StrUtil.isNotEmpty(condition.getCreateTimeStart())) {
            query.addCriteria(Criteria.where("createTime").gte(condition.getCreateTimeStart()));
        }
        if(StrUtil.isNotEmpty(condition.getCreateTimeEnd())) {
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
        Update update = new Update().set("name", user.getName()).set("updateTime", new Date());
        this.mongoTemplate.updateFirst(query, update, UserEntity.class);
    }

    @Override
    public void deleteUserById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        this.mongoTemplate.remove(query, UserEntity.class);
    }

}
