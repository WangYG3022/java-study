package com.wang.mybatis.plugins;

import lombok.Data;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @description: 自己实现分页
 * @author: WANG Y.G.
 * @time: 2020/05/26 23:54
 * @version: V1.0
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class PagePlugin implements Interceptor {

    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<>();

    public static void startPage(int pageNum, int pageSize) {
        LOCAL_PAGE.set(new Page(pageNum, pageSize));
    }

    public static Page endPage() {
        Page page = LOCAL_PAGE.get();
        LOCAL_PAGE.remove();
        return page;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 没有设置分页，直接返回
        if (LOCAL_PAGE.get() == null) {
            return invocation.proceed();
        }
        // 判断目标对象类型
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链。由于目标类可能会被多个插件拦截，从而形成多次代理，通过下面两个循环，可以分理出最原始的目标类
            while (metaStatementHandler.hasGetter("h")) {
                Object h = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(h);
            }

            while (metaStatementHandler.hasGetter("target")) {
                Object target = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(target);
            }

            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            String sql = boundSql.getSql();
            Page page = LOCAL_PAGE.get();
            // 重写SQL
            String pageSql = getPageSql(sql, page);
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);

            Connection connection = (Connection) invocation.getArgs()[0];
            setPageParameter(sql, connection, mappedStatement, boundSql, page);
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object proceed = invocation.proceed();
            Page page = LOCAL_PAGE.get();
            page.setResult((List) proceed);
            return proceed;
        }
        return null;
    }

    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page page) {
        String countSql = "select count(1) from (" + sql + ") temp";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(preparedStatement, mappedStatement, countBoundSql, boundSql.getParameterObject());
            resultSet = preparedStatement.executeQuery();
            int totalCount = 0;
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
            page.setTotal(totalCount);
            int totalPage = (int) Math.ceil(totalCount / page.getPageSize());
            page.setPages(totalPage);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setParameters(PreparedStatement preparedStatement, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(preparedStatement);
    }

    private String getPageSql(String sql, Page page) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from (")
                .append(sql)
                .append(") temp limit ")
                .append(page.getStartRow())
                .append(", ")
                .append(page.getPageSize());
        return buffer.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 自定义Page对象
     * @param <E>
     */
    @Data
    static class Page<E> {
        private int pageNum;
        private int pageSize;
        private int startRow;
        private int endRow;
        private long total;
        private int pages;
        private List<E> result;

        public Page(int pageNum, int pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
            this.endRow = pageNum * pageSize;
        }
    }
}
