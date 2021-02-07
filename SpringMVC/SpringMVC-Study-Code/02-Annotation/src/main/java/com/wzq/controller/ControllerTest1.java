package com.wzq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerTest1 {

    @RequestMapping("/add/{a}/{b}")
    public String add(@PathVariable int a,@PathVariable int b, Model model){
        int res = a + b;
        model.addAttribute("msg","结果为："+res);
        return "hello";
    }

    @PostMapping("/add/{username}")
    public String addPost(@PathVariable String username,Model model){
        model.addAttribute("msg",username);
        return "hello";
    }

}
