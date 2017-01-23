package com.skeeter.demo.designpatterns.proxy;

/**
 * @author michael created on 2016/11/29.
 */
public class RealSubject implements ISubject {
    private String mName;

    public RealSubject(String name) {
        mName = name;
    }

    @Override
    public void doSthing() {
        System.out.println("RealSubject " + mName + " doSthing");
    }
}
