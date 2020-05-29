package com.wang.weadmin.controller;

import com.wang.weadmin.common.AjaxResult;
import com.wang.weadmin.entity.dto.MemberSearchDTO;
import com.wang.weadmin.entity.pojo.Member;
import com.wang.weadmin.entity.vo.PageVO;
import com.wang.weadmin.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 会员管理
 * @author: WANG Y.G.
 * @time: 2020/01/26 22:05
 * @version: V1.0
 */
@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 跳转至会员列表页
     * @return
     */
    @GetMapping("/toListPage")
    public String toListPage() {
        return "html/member/list";
    }

    @GetMapping("/toEditPage")
    public String toEditPage() {
        return "html/member/edit";
    }

    @GetMapping("/toDetailPage")
    public String toDetailPage() {
        return "html/member/detail";
    }

    /**
     * 条件查询
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public AjaxResult list(MemberSearchDTO dto, Integer page, Integer size) {
        if (dto == null) {
            dto = new MemberSearchDTO();
        }
        dto.setPageNum(page);
        dto.setPageSize(size);
        try {
            PageVO<Member> pageVO = memberService.list(dto);
            return AjaxResult.success(pageVO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error();
        }
    }

    /**
     * 新增会员
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public AjaxResult save(@RequestBody Member member) {
        try {
            memberService.save(member);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error();
        }
    }

}
