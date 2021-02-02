package com.wzq.demo2;

public class client {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        UserServiceProxy proxy = new UserServiceProxy();
        proxy.setServiceImpl(service);
        proxy.add();
        proxy.delete();
        proxy.query();
        proxy.update();
    }
}
