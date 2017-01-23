package com.skeeter.demo.designpatterns.factory;

/**
 * @author michael created on 2016/11/30.
 */
public class Factory1 extends AbstractFactory {
    @Override
    public AbstructProductA creatProductA() {
        return new ProductA1();
    }

    @Override
    public AbstructProductB creatProductB() {
        return new ProductB1();
    }
}
