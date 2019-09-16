package com.zhangpingyang.springsecurity.api;

import com.zhangpingyang.springsecurity.bean.react.TopicVo;
import com.zhangpingyang.springsecurity.bean.res.ResponseBean;
import com.zhangpingyang.springsecurity.entity.react.Topic;
import com.zhangpingyang.springsecurity.enumeration.react.TopicType;
import com.zhangpingyang.springsecurity.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/8/23 16:21
 */
@RestController
@RequestMapping(value = "api/V1")
public class ReactController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(value = "collect")
    @PreAuthorize("hasRole('USER')")
    public ResponseBean collect(
//            @RequestParam(value = "accesstoken")String accesstoken,
            @RequestParam(value = "topic_id") String topicId) {
//        System.out.println(accesstoken);
        System.out.println(topicId);

        return null;
    }

    @GetMapping(value = "topics")
    public ResponseBean topics(@RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "tab", required = false) TopicType tab
            , @RequestParam(value = "limit", required = false) Integer limit
            ) {
        Topic condition = new Topic();
        condition.setTab(tab);
        List<Topic> list  =topicService.getTopicList(true, condition, page, limit).getContent();
        ResponseBean bean = new ResponseBean(0, "ok");
//        bean.setData(list);
        ArrayList<TopicVo> rel = new ArrayList<>();
        for (Topic topic : list) {
//            TopicContent topicContent = topic.getTopicContent();
            TopicVo topicVo = new TopicVo(topic.getUser(), topic, false, null);
            rel.add(topicVo);
        }
        bean.setData(rel);
        return bean;
    }
    @GetMapping(value = "topic/{topicId}")
    public ResponseBean topicDetail(@PathVariable(value = "topicId")String topicId) {
        Topic topic = topicService.getTopicAndReplies(topicId);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(0);
        responseBean.setMsg("ok");
        TopicVo topicVo = new TopicVo(topic.getUser(), topic, true, topic.getReplies());
        responseBean.setData(topicVo);
        return responseBean;
    }




}
