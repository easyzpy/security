package com.zhangpingyang.springsecurity.mindview;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/16 14:09
 */
public class FamilyVsExactType {



    static void test(Object x) {
        System.out.println("Testing x of type \t" + x.getClass());
        System.out.println("x instanceof Base \t" + (x instanceof Base));
        System.out.println("x instanceof Derived \t" + (x instanceof Derived));
        System.out.println("Base.isInstance(x) \t" + Base.class.isInstance(x));
        System.out.println("Derived.isInstance(x) \t" + Derived.class.isInstance(x));
        System.out.println("x.getClass() == Base.class \t" + (x.getClass() == Base.class));
        System.out.println("x.getClass() == Derived.class \t" + (x.getClass() == Derived.class));
        System.out.println("x.getClass().equals(Base.class) \t" + x.getClass().equals(Base.class));
        System.out.println("x.getClass().equals(Derived.class) \t" + x.getClass().equals(Derived.class));

    }

    public static void main(String[] args) {
        test(new Base());
        System.out.println();
        test(new Derived());
    }
}


class Base{

}

class Derived extends Base {

}
