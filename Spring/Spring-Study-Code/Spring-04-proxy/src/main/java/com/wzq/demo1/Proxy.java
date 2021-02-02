package com.wzq.demo1;

public class Proxy implements Rent {
    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    public void rent() {
        seeHouse();
        host.rent();
        heTong();
        fee();
    }

    //看房
    public void seeHouse(){
        System.out.println("中介带你看房子");
    }
    //合同
    public void heTong(){
        System.out.println("中介给你签合同");
    }
    //收中介费
    public void fee(){
        System.out.println("中介收你中介费");
    }
}
