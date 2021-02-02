package com.wzq.demo2;

public class UserServiceProxy implements UserService {

    private UserServiceImpl serviceImpl;

    public void setServiceImpl(UserServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public void add() {
        log("add");
        serviceImpl.add();
    }

    public void delete() {
        log("delete");
        serviceImpl.delete();
    }

    public void update() {
        log("update");
        serviceImpl.update();
    }

    public void query() {
        log("query");
        serviceImpl.query();
    }

    //日志
    public void log(String msg){
        System.out.println("[Debug] 调用了" + msg + "方法");
    }
}
