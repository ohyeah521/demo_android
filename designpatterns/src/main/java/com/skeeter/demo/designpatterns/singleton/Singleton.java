package com.skeeter.demo.designpatterns.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author michael created on 2016/11/29.
 */
public class Singleton {

    /**
     * 1. 懒汉模式
     * 优点：只有在使用时才会实例化
     * 缺点：第一次加载时及时进行初始化，反应稍慢；每次调用{@link #getInstance()}时都进行同步，同步开销太大
     */
    public static class Singleton1 {
        private static Singleton1 sInstance;

        private Singleton1() {}

        public static synchronized Singleton1 getInstance() {
            if (sInstance == null) {
                sInstance = new Singleton1();
            }
            return sInstance;
        }
    }

    /**
     * 2. 饿汉模式
     * 优点：利用ClassLoader的机制，避免了多线程同步问题
     * 缺点：类加载时就进行了实例化，没有实现lazy loading的效果
     */
    public static class Singleton2 {
        private static Singleton2 sInstance = new Singleton2();

        private Singleton2() {}

        public static Singleton2 getInstance() {
            return sInstance;
        }
    }

    /**
     * 3. 双重校验锁
     * 优点：既保证了延迟加载又保证了多线程安全，资源利用率高，效率高，没有多余的线程同步
     * 缺点：第一次加载时反应稍慢
     */
    public static class Singleton3 {
        private static Singleton3 sInstance;

        private Singleton3() {}

        public static Singleton3 getInstance() {
            if (sInstance == null) {
                synchronized (Singleton3.class) {
                    if (sInstance == null) {
                        sInstance = new Singleton3();
                    }
                }
            }
            return sInstance;
        }
    }

    /**
     * 4. 静态内部类
     * 优点：利用ClassLoader类加载机制，保证了单例的唯一性，延迟加载，多线程安全
     * 没有明显的缺陷，最推荐的方式
     */
    public static class Singleton4 {
        private Singleton4() {}

        public static Singleton4 getInstance() {
            return Singleton4Holder.sInstance;
        }

        private static class Singleton4Holder {
            private static final Singleton4 sInstance = new Singleton4();
        }
    }

    /**
     * 5. 枚举
     * 优点：枚举实例的创建时线程安全的，反序列化也会创建新对象，不能被反射，简直无懈可击
     * 没有明显的缺陷
     */
    public static enum Singleton5 {
        INSTANCE;

        public void doSth() {
            // ...
        }
    }

    /**
     * 6. 容器，单例管理器
     * 可以管理多中类型的单例，降低了用户的使用成本，隐藏了具体实现，降低了耦合
     *
     * Key, 可以用{@link Class#getName()}代替
     */
    public static class SingletonManager {
        private static Map<Class, Object> sSingletonMap = new ConcurrentHashMap<>();

        private SingletonManager() {}

        public static void rigisterSingleton(Class clazz, Object instance) {
            if (!sSingletonMap.containsKey(clazz)) {
                sSingletonMap.put(clazz, instance);
            }
        }

        public static Object getInstance(Class clazz) {
            return sSingletonMap.get(clazz);
        }
    }

}
