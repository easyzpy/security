package com.zhangpingyang.springsecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhangpingyang.springsecurity.bean.react.TopicVo;
import com.zhangpingyang.springsecurity.bean.res.ResponseBean;
import com.zhangpingyang.springsecurity.bean.res.ResponsePage;
import com.zhangpingyang.springsecurity.entity.react.Topic;
import com.zhangpingyang.springsecurity.enumeration.react.TopicType;
import com.zhangpingyang.springsecurity.service.TopicService;
import com.zhangpingyang.springsecurity.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/16 15:43
 */
@Controller
@RequestMapping("topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping(value = "list")
    @PreAuthorize("hasRole('TOURIST')")
    public String list(
            @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "tab", required = false) TopicType tab
            , @RequestParam(value = "limit", required = false) Integer limit
            , ModelMap model
            ) {
        Topic condition = new Topic();
        condition.setTab(tab);
        PageImpl<Topic> topicList = (PageImpl<Topic>) topicService.getTopicList(true, condition, page, limit);
        ResponsePage bean = new ResponsePage(0, "ok");
        bean.setTotalPage(topicList.getTotalPages());
        bean.setPage(topicList.getNumber());
        bean.setTotalRecord(topicList.getTotalElements());
        ArrayList<TopicVo> rel = new ArrayList<>();
        for (Topic topic : topicList.getContent()) {
            TopicVo topicVo = new TopicVo(topic.getUser(), topic, false, null);
            rel.add(topicVo);
        }
        bean.setData(rel);
        try {
            System.out.println(ObjectMapperUtil.getObjectMapper().writeValueAsString(bean));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.put("bean", bean);
        return "topic/topic_list";
    }
}
