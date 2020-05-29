package com.wang.mybatis.plugins;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @description: 实现自定义Mybatis插件。打印SQL语句
 * @author: WANG Y.G.
 * @time: 2020/05/26 22:46
 * @version: V1.0
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class PrintPlugin implements Interceptor {

    /*
     * Mybatis允许进行拦截的接口：
     * Executor、ParameterHandler、ResultHandler、StatementHandler
     */


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        System.out.println("PrintPlugin: boundSql = " + boundSql.getSql());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}



