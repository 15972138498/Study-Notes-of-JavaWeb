package com.wzq.controller;

import com.wzq.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerTest2 {

    /*
    @RequestMapping("/test")
    public String Test1(@RequestParam("username") String name){
        System.out.println(name);
        return "hello";
    }
    */

    @RequestMapping("/test")
    public String Test2(User user){
        System.out.println(user);
        return "hello";
    }

}
