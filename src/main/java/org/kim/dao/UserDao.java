package org.kim.dao;

import org.apache.ibatis.annotations.Param;
import org.kim.entity.User;

public interface UserDao {
    public User getUser(@Param("username") String username, @Param("password") String password);
}
