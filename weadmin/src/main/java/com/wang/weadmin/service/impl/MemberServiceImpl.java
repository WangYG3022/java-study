package com.wang.weadmin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wang.weadmin.entity.dto.MemberSearchDTO;
import com.wang.weadmin.entity.pojo.Member;
import com.wang.weadmin.entity.pojo.MemberExample;
import com.wang.weadmin.entity.vo.PageVO;
import com.wang.weadmin.event.MemberRegisterEvent;
import com.wang.weadmin.mapper.MemberMapper;
import com.wang.weadmin.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 会员管理service
 * @author: WANG Y.G.
 * @time: 2020/01/28 15:31
 * @version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ApplicationContext context;

    @Override
    public PageVO<Member> list(MemberSearchDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        MemberExample example = new MemberExample();
        example.setOrderByClause("id asc");
        MemberExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(dto.getName())) {
            criteria.andNameLike(dto.getName().trim() + "%");
        }
        if (null != dto.getAge()) {
            criteria.andAgeEqualTo(dto.getAge());
        }
        Page<Member> page = (Page<Member>) memberMapper.selectByExample(example);
        return new PageVO<>(page.getTotal(), page.getResult());
    }

    @Override
    public void save(Member member) {
        memberMapper.insertSelective(member);
        MemberRegisterEvent memberRegisterEvent = new MemberRegisterEvent(member);
        context.publishEvent(memberRegisterEvent);
    }

}
