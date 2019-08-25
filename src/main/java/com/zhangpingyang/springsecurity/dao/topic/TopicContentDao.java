package com.zhangpingyang.springsecurity.dao.topic;


import com.zhangpingyang.springsecurity.entity.react.TopicContent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicContentDao extends JpaRepository<TopicContent, String> {
}
