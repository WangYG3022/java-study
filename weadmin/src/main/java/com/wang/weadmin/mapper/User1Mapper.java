package com.wang.weadmin.mapper;

import com.wang.weadmin.entity.pojo.User1;
import com.wang.weadmin.entity.pojo.User1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface User1Mapper {
    long countByExample(User1Example example);

    int deleteByExample(User1Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(User1 record);

    int insertSelective(User1 record);

    List<User1> selectByExample(User1Example example);

    User1 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User1 record, @Param("example") User1Example example);

    int updateByExample(@Param("record") User1 record, @Param("example") User1Example example);

    int updateByPrimaryKeySelective(User1 record);

    int updateByPrimaryKey(User1 record);
}