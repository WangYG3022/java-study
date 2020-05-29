package com.wang.mybatis.mapper;

import com.wang.mybatis.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheNamespace(blocking = true) //
public interface UserMapper {
    /**
     * 按ID查询
     * @param userId 主键ID
     * @return User
     */
    @Select("select * from user where user_id = #{userId}")
    User getUserById(Integer userId);

    /**
     * 根据ID修改用户信息
     * @param userId 主键ID
     * @param userName 新用户名
     */
    @Update("update user set user_name = #{userName} where user_id = #{userId}")
    void updateUserById(Integer userId, String userName);

    /**
     * 获取所有用户信息
     * @return
     */
    @Select("select * from user")
    List<User> getAll();
}