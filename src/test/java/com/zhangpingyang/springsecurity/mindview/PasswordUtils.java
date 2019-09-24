package com.zhangpingyang.springsecurity.mindview;

import com.zhangpingyang.springsecurity.mindview.annotation.UseCase;

import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/17 11:13
 */
public class PasswordUtils {
    @UseCase(id = 47, description = "Passwords must contain at least one numeric")
    public boolean validatePassword(String password){
        if (password == null) {
            return false;
        }
        return password.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49, description = "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(String password, List<String> prevPasswords) {
        return !prevPasswords.contains(password);
    }

}
