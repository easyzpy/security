package com.zhangpingyang.springsecurity.dao.topic;

import com.zhangpingyang.springsecurity.entity.react.Reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyDao extends JpaRepository<Reply, String> {
}
