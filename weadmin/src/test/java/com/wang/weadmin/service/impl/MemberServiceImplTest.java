package com.wang.weadmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wang.weadmin.WeAdminApplication;
import com.wang.weadmin.common.enums.member.MemberLevelEnum;
import com.wang.weadmin.common.enums.member.MemberSourceEnum;
import com.wang.weadmin.entity.dto.MemberSearchDTO;
import com.wang.weadmin.entity.pojo.Member;
import com.wang.weadmin.entity.vo.PageVO;
import com.wang.weadmin.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeAdminApplication.class)
public class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void list() {
        MemberSearchDTO dto = new MemberSearchDTO();
        dto.setName("会");
        dto.setPageNum(1);
        dto.setPageSize(5);
        PageVO<Member> pageVO = memberService.list(dto);
        System.out.println(JSONObject.toJSONString(pageVO));
    }

    @Test
    public void save() {
        for (int i = 20; i < 21; i++) {
            Member member = new Member();
            member.setName("会员：" + (i + 1));
            member.setAge(20 + i);
            member.setMobile("12345678901");
            member.setBirthday(LocalDate.now().minusMonths(Long.parseLong(i + "")));
            member.setLevel(MemberLevelEnum.NORMAL.getLevel());
            member.setSource(MemberSourceEnum.PC.getSource());
            memberService.save(member);
        }
    }

    @Test
    public void test() {
        String ids = "[1, 2, 3]";
        List<Integer> idList = JSONArray.parseArray(ids, Integer.class);
        System.out.println(idList);
    }

}