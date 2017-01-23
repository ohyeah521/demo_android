package com.skeeter.demo.designpatterns.factory;

/**
 * @author michael created on 2016/11/30.
 */
public class Factory2 extends AbstractFactory {
    @Override
    public AbstructProductA creatProductA() {
        return new ProductA2();
    }

    @Override
    public AbstructProductB creatProductB() {
        return new ProductB2();
    }
}
