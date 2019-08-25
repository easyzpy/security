package com.zhangpingyang.springsecurity.dao.topic;

import com.zhangpingyang.springsecurity.entity.react.Topic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicDao extends JpaRepository<Topic, String> {
}
