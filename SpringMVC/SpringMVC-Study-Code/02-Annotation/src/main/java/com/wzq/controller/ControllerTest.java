package com.wzq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller注解的类会自动添加到Spring的上下文中
@Controller
public class ControllerTest {

    //映射访问路径
    @RequestMapping("/h1")
    public String index(Model model){
        //Spring MVC 会自动实例化一个Model对象用于视图中传值
        model.addAttribute("msg","hello SpringMVC");
        //返回视图位置
        return "hello";
    }

    /*
    * 还可以配置多个
    * */
}
