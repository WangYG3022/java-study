package com.wang.weadmin.listener;

import com.wang.weadmin.entity.pojo.Member;
import com.wang.weadmin.event.MemberRegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/02/07 15:50
 * @version: V1.0
 */
@Component
public class MemberRegisterListener implements ApplicationListener<MemberRegisterEvent> {
    @Override
    public void onApplicationEvent(MemberRegisterEvent memberRegisterEvent) {
        Object source = memberRegisterEvent.getSource();
        if (source instanceof Member) {
            Member member = (Member) source;
            System.out.println("给会员发送注册成功短信，手机号码：" + member.getMobile());
        }
    }
}
