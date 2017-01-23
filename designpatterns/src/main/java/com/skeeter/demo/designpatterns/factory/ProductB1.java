package com.skeeter.demo.designpatterns.factory;

/**
 * @author michael created on 2016/11/30.
 */
public class ProductB1 extends AbstructProductB {
    @Override
    public void doSthB() {
        System.out.println(getClass().getSimpleName() + " doSthB ");
    }
}
