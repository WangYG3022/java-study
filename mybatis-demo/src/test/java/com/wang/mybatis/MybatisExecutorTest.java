package com.wang.mybatis;

import com.wang.mybatis.pojo.User;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @description: Mybatis执行器学习
 * @author: WANG Y.G.
 * @time: 2020/05/25 21:45
 * @version: V1.0
 */
public class MybatisExecutorTest {

    private SqlSession sqlSession;
    private Configuration configuration;
    private JdbcTransaction transaction;

    @Before
    public void init() throws IOException {
        // 1.加载主配置文件
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2.创建SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 3.创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 4.获取session
        sqlSession = sqlSessionFactory.openSession(true);
        // openSession时可以指定Executor类型，默认为：ExecutorType.SIMPLE
//        sqlSessionFactory.openSession(ExecutorType.REUSE, true);

        configuration = sqlSessionFactory.getConfiguration();
        Connection connection = sqlSession.getConnection();
        transaction = new JdbcTransaction(connection);
    }

    /*
     * Mybatis执行器有3种（见org.apache.ibatis.session.ExecutorType）：
     * 1. SIMPLE：无论SQL是否一样，每次都会对SQL进行预编译。
     * 2. REUSE：相同的SQL只进行一次预编译。
     * 3. BATCH：相同SQL只预编译一次，必须手动刷新（doFlushStatements）才会生效。
     */

    /**
     * 简单执行器
     */
    @Test
    public void simpleExecutor() throws SQLException {
        // 构造简单执行器
        SimpleExecutor executor = new SimpleExecutor(configuration, transaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.wang.mybatis.mapper.UserMapper.getUserById");
        // 第一次调用SQL
        List<Object> list = executor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
        System.out.println(list.get(0));
        // 第二次调用SQL
        // 日志显示两次都Preparing SQL语句。即：无论SQL是否一样，简单执行器每次都会预编译。
        executor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
    }

    /**
     * 可复用执行器
     */
    @Test
    public void reuseExecutor() throws SQLException {
        // 构造可复用执行器
        ReuseExecutor executor = new ReuseExecutor(configuration, transaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.wang.mybatis.mapper.UserMapper.getUserById");
        // 第一次调用SQL
        List<Object> list = executor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
        System.out.println(list.get(0));
        // 第二次调用SQL
        // 日志显示第一次Preparing SQL语句。即：可复用执行器只在第一次调用时进行预编译。
        executor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
    }

    /**
     * 批处理执行器，只针对修改操作
     */
    @Test
    public void batchExecutor() throws SQLException {
        // 构造可复用执行器
        BatchExecutor executor = new BatchExecutor(configuration, transaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.wang.mybatis.mapper.UserMapper.updateUserById");
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", 3);
        param.put("userName", "王五");
        // 第一次调用SQL
        executor.doUpdate(mappedStatement, param);
        // 第二次调用SQL
        // 日志显示第一次Preparing SQL语句。即：可复用执行器只在第一次调用时进行预编译。
        executor.doUpdate(mappedStatement, param);
        // 批处理操作必须手动刷新，提交
        executor.doFlushStatements(false);
    }

    /**
     * 一级缓存逻辑
     * @throws SQLException
     */
    @Test
    public void baseExecutor() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, transaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.wang.mybatis.mapper.UserMapper.getUserById");
        // 第一次调用SQL
        List<Object> list = executor.query(mappedStatement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        System.out.println(list.get(0));
        // 第二次调用SQL
        // 只预编译一次，第二次走本地一级缓存
        executor.query(mappedStatement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    }

    /**
     * 二级缓存逻辑
     * @throws SQLException
     */
    @Test
    public void cachingExecutor() throws SQLException {
        Executor executor = new ReuseExecutor(configuration, transaction);
        // 装饰者模式
        Executor cachingExecutor = new CachingExecutor(executor);
        MappedStatement mappedStatement = configuration.getMappedStatement("com.wang.mybatis.mapper.UserMapper.getUserById");
        // 第一次调用SQL
        List<Object> list = cachingExecutor.query(mappedStatement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        // 将查询到的数据提交到二级缓存
        cachingExecutor.commit(true);
        // 第二次调用SQL
        // 先查二级缓存，再查一级缓存，都没有时才查数据库。
        cachingExecutor.query(mappedStatement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        cachingExecutor.query(mappedStatement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    }

    @Test
    public void sqlSession() {
        User user = sqlSession.selectOne("com.wang.mybatis.mapper.UserMapper.getUserById", 1);
        System.out.println(user);
    }
}
