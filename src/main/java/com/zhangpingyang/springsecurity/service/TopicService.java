package com.zhangpingyang.springsecurity.service;

import com.zhangpingyang.springsecurity.dao.topic.ReplyDao;
import com.zhangpingyang.springsecurity.dao.topic.TopicDao;
import com.zhangpingyang.springsecurity.entity.react.Reply;
import com.zhangpingyang.springsecurity.entity.react.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private ReplyDao replyDao;
    public List<Topic> getTopicList(boolean hasTopicContent, Topic condition, Integer page, Integer limit) {
        Example<Topic> example = Example.of(condition);
        Sort orders = new Sort(Sort.Direction.DESC, "createTime");
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 40;
        }
        Page<Topic> all = topicDao.findAll(example, PageRequest.of(page, limit, orders));
        return all.getContent();

    }

    public Topic getTopicAndReplies(String topicId) {
        Topic topic = topicDao.getOne(topicId);
        if (topic == null) {
            return null;
        }
        Reply condition = new Reply();
        condition.setTopic(topic);
        Example<Reply> example = Example.of(condition);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        List<Reply> all = replyDao.findAll(example, sort);
        topic.setReplies(all);
        return topic;
    }
}
