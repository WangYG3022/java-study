package com.wang.weadmin.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 页面展示VO
 * @author: WANG Y.G.
 * @time: 2019/11/25 11:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageVO<T> implements Serializable {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 本页数据集
     */
    private List<T> rows;
}
