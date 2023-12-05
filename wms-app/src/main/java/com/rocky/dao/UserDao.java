package com.rocky.dao;

import com.rocky.model.User;
import com.rocky.model.UserLoginInfo;
import com.rocky.model.UserRegiserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface UserDao {
    //增加用户
    int addUser(UserRegiserInfo userRegiserInfo) ;

    User select(String tel) ;

    //登录
    User login(UserLoginInfo userLoginInfo) ;
}
