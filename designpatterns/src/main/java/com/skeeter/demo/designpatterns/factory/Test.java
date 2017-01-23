package com.skeeter.demo.designpatterns.factory;

/**
 * @author michael created on 2016/11/30.
 */
public class Test {

    public static void main(String[] args) {
//        AbstractFactory factory = new Factory1();

        createAndInvoke(new Factory1());
        createAndInvoke(new Factory2());
    }

    public static void createAndInvoke(AbstractFactory factory) {
        AbstructProductA productA = factory.creatProductA();
        AbstructProductB productB = factory.creatProductB();
        productA.doSthA();
        productB.doSthB();
    }

}
