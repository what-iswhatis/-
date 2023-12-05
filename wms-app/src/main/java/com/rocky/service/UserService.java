package com.rocky.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.jwt.JWTUtil;
import com.rocky.dao.UserDao;
import com.rocky.dao.UserRoleDao;
import com.rocky.exception.Bizexception;
import com.rocky.model.User;
import com.rocky.model.UserLoginInfo;
import com.rocky.model.UserRegiserInfo;
import com.rocky.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Service
public class UserService {
    @Autowired
    public UserDao userDao;


    //token密钥
    @Value("${token.key}")
    private String tokenKey;

    @Autowired
    private UserRoleDao userRoleDao;


    //redis拿过来
    @Resource(name = "redisTemplate")
    private ValueOperations<String,String> valueOperations;
    public Integer addUser(UserRegiserInfo userRegiserInfo) {
        //1: 验证数据格式的正确性

        //2: 验证验证码的正确性(输入的验证码是否和redis里存的验证码一致。如果不一致，抛出异常)
        String vcode = userRegiserInfo.getVcode();
        String redis_vcode = valueOperations.get("sms." + userRegiserInfo.getTel());
        if (!vcode.equals(redis_vcode)){
            throw new Bizexception(401,"验证码不正确");
        }


        //3：验证一个手机号一天只能发送五次验证码
        if (valueOperations.get("sms." + userRegiserInfo.getTel())!= null){
            int count = Integer.parseInt(valueOperations.get("sms." + userRegiserInfo.getTel()));
            if (count >= 5){
                throw new Bizexception(403,"一天只能发送五次验证码");
            }
        }


        //4：验证用户名是否重复
        User user = userDao.select(userRegiserInfo.getTel());
        if (user != null){
            throw new Bizexception(402,"手机号已注册");
        }
        return userDao.addUser(userRegiserInfo);
    }


    // 登录
    public String login(UserLoginInfo loginInfo ) {
        //1: 验证数据格式的正确性
        User user = userDao.login(loginInfo);
        if (ObjUtil.isEmpty(user)){
            throw new Bizexception(403,"手机号或密码不正确！");
        }

        List<UserRole> userRoleList = userRoleDao.select(user.getId());
        List<String> roles =userRoleList.stream().map(UserRole::getName).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("id", user.getId());
                put("nickName", user.getNickName());
                put("roles", roles);
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, tokenKey.getBytes());

        return token;
    }
}
