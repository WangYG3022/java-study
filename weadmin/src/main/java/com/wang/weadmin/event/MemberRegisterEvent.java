package com.wang.weadmin.event;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 会员注册事件
 * @author: WANG Y.G.
 * @time: 2020/02/07 15:43
 * @version: V1.0
 */
public class MemberRegisterEvent extends ApplicationEvent {

    public MemberRegisterEvent(Object source) {
        super(source);
    }

}
