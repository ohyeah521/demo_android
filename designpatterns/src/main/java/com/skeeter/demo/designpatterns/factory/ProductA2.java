package com.skeeter.demo.designpatterns.factory;

/**
 * @author michael created on 2016/11/30.
 */
public class ProductA2 extends AbstructProductA {
    @Override
    public void doSthA() {
        System.out.println(getClass().getSimpleName() + " doSthA ");
    }
}
