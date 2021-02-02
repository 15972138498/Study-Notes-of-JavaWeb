package com.wzq.demo1;

public class client {
    public static void main(String[] args) {
        //房东要出租房子
        Host host = new Host();
        //代理
        Proxy proxy = new Proxy(host);
        //你直接找中介
        proxy.rent();
    }
}
