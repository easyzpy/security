package com.zhangpingyang.springsecurity.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/20 18:30
 */
public class NormalHandler implements InvocationHandler {
    private Object target;

    public NormalHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("may say invoked at " + System.currentTimeMillis());
        method.invoke(target, args);
//        method.null
        return null;
    }
}
