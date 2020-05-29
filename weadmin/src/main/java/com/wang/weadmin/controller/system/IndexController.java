package com.wang.weadmin.controller.system;

import com.wang.weadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/01/27 11:52
 * @version: V1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(/*Model model*/) {
//        model.addAttribute("title", "首页");
//        model.addAttribute("user", userService.getUserById(35L));
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "html/welcome";
    }
}
