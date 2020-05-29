package com.wang.weadmin.entity.dto;

import lombok.Data;

/**
 * @description: 会员管理查询条件
 * @author: WANG Y.G.
 * @time: 2020/01/28 15:36
 * @version: V1.0
 */
@Data
public class MemberSearchDTO extends BaseSearchDTO {

    private String name;

    private Integer age;

}
