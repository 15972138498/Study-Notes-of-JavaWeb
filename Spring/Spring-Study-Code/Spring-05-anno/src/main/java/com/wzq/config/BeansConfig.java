package com.wzq.config;

import com.wzq.pojo.Dog;
import com.wzq.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//指定这个类为配置类
@Configuration
//扫描包
@ComponentScan("com.wzq.pojo")
public class BeansConfig {



    @Bean
    public Dog dog(){
        return new Dog();
    }
}
