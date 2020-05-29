package com.wang.mybatis;

import com.wang.mybatis.mapper.UserMapper;
import com.wang.mybatis.plugins.PagePlugin;
import com.wang.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description: Mybatis插件
 * @author: WANG Y.G.
 * @time: 2020/05/26 22:57
 * @version: V1.0
 */
public class MybatisPluginTest {

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        sqlSession = sqlSessionFactory.openSession(true);
    }

    @Test
    public void plugin() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById(1);
        System.out.println(user);
        PagePlugin.startPage(1, 2);
        List<User> list = userMapper.getAll();
        PagePlugin.endPage();
        System.out.println(list);
    }
}
