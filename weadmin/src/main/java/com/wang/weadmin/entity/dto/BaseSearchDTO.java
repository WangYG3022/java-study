package com.wang.weadmin.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 基础查询条件DTO
 * @author: WANG Y.G.
 * @time: 2020/01/28 15:34
 * @version: V1.0
 */
@Data
public class BaseSearchDTO implements Serializable {

    private Integer pageNum;

    private Integer pageSize;

}
