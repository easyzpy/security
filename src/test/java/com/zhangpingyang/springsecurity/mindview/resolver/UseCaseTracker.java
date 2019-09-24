package com.zhangpingyang.springsecurity.mindview.resolver;

import com.zhangpingyang.springsecurity.mindview.annotation.UseCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/17 11:28
 *
 * UserCase的注解处理器
 */
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            UseCase uc = method.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found Use Case:" + uc.id() + "   " + uc.description());
//                useCases.remove(new Integer(uc.id()));
            }
        }
        for (int integer : useCases) {
            System.out.println("Warning: Missing use case-" + integer);
        }
    }



    public static void main(String[] args) {
        ArrayList<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases, 47, 48, 49, 50);
//        trackUseCases(useCases, PasswordUtils.class);

    }
}
