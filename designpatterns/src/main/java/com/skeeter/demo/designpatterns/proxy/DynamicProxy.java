package com.skeeter.demo.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author michael created on 2016/11/29.
 */
public class DynamicProxy implements InvocationHandler {
    /**
     * 被代理类
     */
    private ISubject mTarget;

    public DynamicProxy(ISubject target) {
        mTarget = target;
    }

    /**
     * 动态代理，代理了被代理者的多有方法
     *
     * @param proxy 动态生成的代理, 不要在{@link #invoke(Object, Method, Object[])}方法内调用proxy的方法，
     *              包括{@link Object#toString()}, 否则会导致循环调用
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        System.out.println("proxy = " + proxy + " while mTarget: " + mTarget);

        Object result = method.invoke(mTarget, args);
        System.out.println("result of method invoked: " + result);

        return result;
    }

    public static void main(String[] args) {
        RealSubject target = new RealSubject("michael");
        InvocationHandler handler = new DynamicProxy(target);

        ISubject proxy = (ISubject) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);

        System.out.println("proxy = " + proxy);
        System.out.println("proxy == target? " + (proxy == target) + " proxy.hashCode == target.hashCode? " +
                (proxy.hashCode() == target.hashCode()));

        proxy.doSthing();
    }
}
