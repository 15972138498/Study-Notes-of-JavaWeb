package com.wzq.pojo;

import javax.annotation.Resource;

public class Person {
    private String name;
    @Resource(name = "dog1")
    private Dog dog;
    @Resource(name = "cat1")
    private Cat cat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //没有了set狗和猫的方法
    public Dog getDog() {
        return dog;
    }

    public Cat getCat() {
        return cat;
    }

}
