package com.wang.weadmin.service;

import com.wang.weadmin.entity.dto.MemberSearchDTO;
import com.wang.weadmin.entity.pojo.Member;
import com.wang.weadmin.entity.vo.PageVO;

/**
 * @description: 会员管理service
 * @author: WANG Y.G.
 * @time: 2020/01/28 15:31
 * @version: V1.0
 */
public interface MemberService {

    /**
     * 会员条件查询
     * @param dto
     * @return
     */
    PageVO<Member> list(MemberSearchDTO dto);

    /**
     * 新增会员
     * @param member
     */
    void save(Member member);

}
