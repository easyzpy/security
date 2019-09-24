package com.zhangpingyang.springsecurity.mindview.resolver;

import com.zhangpingyang.springsecurity.mindview.annotation.Constraints;
import com.zhangpingyang.springsecurity.mindview.annotation.DBTable;
import com.zhangpingyang.springsecurity.mindview.annotation.SQLInteger;
import com.zhangpingyang.springsecurity.mindview.annotation.SQLString;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/17 14:51
 *
 *  create table member(firstname varchar(30))
 */
public class TableCreator {

    public String createTable(Class<?> c) {
        DBTable dbTableAnnotation = c.getAnnotation(DBTable.class);
        if (dbTableAnnotation == null) {
            System.out.println("No DBTable annotations in class [" + c +"]");
            return null;
        }
        String tbname = dbTableAnnotation.name();
        if (tbname.length() < 1) {
            tbname = c.getName();
        }
        Field[] declaredFields = c.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            System.out.println("["+c.getName()+"] has no field");
            return null;
        }
        List<String> columnDefs = new ArrayList<>();
        for (Field field : declaredFields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            if (declaredAnnotations.length == 0) {
                System.out.println("["+field.getName()+"] is not db column");
                continue;
            }
            for (Annotation fieldAnnotation : declaredAnnotations) {
                if (fieldAnnotation instanceof SQLInteger) {
                    //use field name if name not specified
                    SQLInteger anoInt = (SQLInteger) fieldAnnotation;
                    String columnName ;
                    if (anoInt.name().length() == 0) {
                        columnName = field.getName();
                    } else {
                        columnName = anoInt.name();
                    }
//                    `commentId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                    columnDefs.add(columnName + " int " + getConstraints(anoInt.constraints()));

                }
                if (fieldAnnotation instanceof SQLString) {
                    SQLString strAno = (SQLString) fieldAnnotation;
                    String columnName ;
                    if (strAno.name().length() == 0) {
                        columnName = field.getName();
                    } else {
                        columnName = strAno.name();
                    }
                    columnDefs.add(columnName + " varchar("+ strAno.value() +")" + getConstraints(strAno.constraints()));
                }
            }
        }
        StringBuilder createTableCMD = new StringBuilder("CREATE TABLE `" + tbname + "` (");
        for (String columnDef : columnDefs) {
            createTableCMD.append(columnDef).append(",");
        }
        String substring = createTableCMD.substring(0, createTableCMD.length() - 1);
        //remove trailing comma
        substring += ") ENGINE = InnoDB CHARACTER SET = utf8mb4";
        return substring;
    }

    private String getConstraints(Constraints constraints) {
        StringBuilder sb = new StringBuilder();
        if (constraints.primaryKey()) {
            sb.append(" PRIMARY KEY");
        }
        if (!constraints.allowNull()) {
            sb.append(" not null");
        }
        if (constraints.unique()) {
            sb.append(" unique");
        }
        return sb.toString();
    }

    @Test
    public void test1() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.zhangpingyang.springsecurity.mindview.Customentity.Member");
        String createTableSql = createTable(aClass);
        System.out.println(createTableSql);

    }


}
