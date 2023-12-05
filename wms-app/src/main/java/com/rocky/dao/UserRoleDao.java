package com.rocky.dao;

import com.rocky.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface UserRoleDao {
    @Select("select r2.id , r2.name  from `208_user` u left join `208_user_roles` r on u.id = r.user_id  left join `208_roles` r2  on r.role_id = r2.id  where u.id =#{id}")
    List<UserRole> select(Integer userId);
}
