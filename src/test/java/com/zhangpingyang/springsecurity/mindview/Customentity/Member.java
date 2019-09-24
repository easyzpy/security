package com.zhangpingyang.springsecurity.mindview.Customentity;

import com.zhangpingyang.springsecurity.mindview.annotation.Constraints;
import com.zhangpingyang.springsecurity.mindview.annotation.DBTable;
import com.zhangpingyang.springsecurity.mindview.annotation.SQLInteger;
import com.zhangpingyang.springsecurity.mindview.annotation.SQLString;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/17 14:37
 */
@DBTable(name = "tb_member")
public class Member {
    @SQLString(30)
    private String firstName;
    @SQLString(50)
    private String lastName;
    @SQLInteger
    private Integer age;
    @SQLString(value = 30
    ,constraints = @Constraints(primaryKey = true))
    private String handle;
    static int memberCount;

    @Override
    public String toString() {
        return handle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getHandle() {
        return handle;
    }
}
