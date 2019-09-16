package com.zhangpingyang.springsecurity.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/11 12:47
 */
@Configuration
public class MongodbConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("106.15.238.84", 27017);
    }

    @Override
    protected String getDatabaseName() {
        return "School";
    }
}
