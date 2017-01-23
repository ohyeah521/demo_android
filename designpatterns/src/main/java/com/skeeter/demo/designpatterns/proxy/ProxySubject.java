package com.skeeter.demo.designpatterns.proxy;

/**
 * @author michael created on 2016/11/29.
 */
public class ProxySubject implements ISubject {
    private ISubject mTarget;

    public ProxySubject(ISubject target) {
        mTarget = target;
    }

    @Override
    public void doSthing() {
        System.out.println("ProxySubject invoke method doSthing");
        mTarget.doSthing();
    }

    public static void main(String[] args) {
        RealSubject subject = new RealSubject("skeeter");
        ProxySubject proxy = new ProxySubject(subject);
        proxy.doSthing();
    }
}
