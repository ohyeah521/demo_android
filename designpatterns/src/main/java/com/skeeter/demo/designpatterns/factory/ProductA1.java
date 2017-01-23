package com.skeeter.demo.designpatterns.factory;

/**
 * @author michael created on 2016/11/30.
 */
public class ProductA1 extends AbstructProductA {
    @Override
    public void doSthA() {
        System.out.println(getClass().getSimpleName() + " doSthA ");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ProductA1 productA1 = (ProductA1) super.clone();
        productA1.temp = (TempClss) this.temp.clone();
        return productA1;
    }

    public static class TempClss implements Cloneable {
        String temp;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    TempClss temp = new TempClss();

    public static void main(String[] args) {
        ProductA1 a1 = new ProductA1();
        a1.temp.temp = "dasj";
        System.out.println("a1.temp.temp = " + a1.temp.temp);

        try {
            ProductA1 a11 = (ProductA1) a1.clone();
            a11.temp.temp = "sfsfsd";
            System.out.println("a1.temp == a11.temp? " + (a1.temp == a11.temp));
            System.out.println("a11.temp.temp = " + a11.temp.temp);
            System.out.println("a1.temp.temp = " + a1.temp.temp);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }
}
