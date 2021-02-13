package com.wzq.config;

import com.wzq.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送 / 或 /login.html 访问到 index
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");

        //请求main.html 跳转到 dashboard.html
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        * addPathPatterns：拦截的请求
        * excludePathPatterns：排除哪些请求
        * 如果是/*： 静态资源：*.css  *.js，SpringBoot已经做好了静态资源映射，这里不用排除,!!!，只过滤下一级目录
        * 如果是/** ：需要排除，过滤所有目录
         */
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/user/login","/asserts/css/**","/asserts/js/**","/asserts/img/**");
    }
}
