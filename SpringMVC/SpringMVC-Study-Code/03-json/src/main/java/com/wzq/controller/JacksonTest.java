package com.wzq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzq.utils.JsonUtil;
import com.wzq.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
@RestController //这个注解标注下列方法，不走视图解析器，只返回字符串
public class JacksonTest {

    @RequestMapping("/j1")
    //@ResponseBody
    // 加上这个注解，会直接返回一个字符串
    public String Test1() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1,"wzq",18);
        return mapper.writeValueAsString(user);
    }

    //集合
    @RequestMapping("/j2")
    public String Test2() throws JsonProcessingException {
        User user1 = new User(1,"wzq1",18);
        User user2 = new User(2,"wzq2",18);
        User user3 = new User(3,"wzq3",18);
        User user4 = new User(4,"wzq4",18);
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        //可以直接写为一行
        return new ObjectMapper().writeValueAsString(list);
    }

    //时间
    @RequestMapping("/j3")
    public String Test3() throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new ObjectMapper().writeValueAsString(sdf.format(date));
    }

    //使用工具类
    @RequestMapping("/j4")
    public String Test4(){
        return JsonUtil.getJson(new User(1,"wzq",3));
    }
}
