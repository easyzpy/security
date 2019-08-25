package com.zhangpingyang.springsecurity.bean.react;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.entity.react.Reply;
import com.zhangpingyang.springsecurity.entity.react.Topic;
import com.zhangpingyang.springsecurity.entity.react.TopicContent;
import com.zhangpingyang.springsecurity.enumeration.react.TopicType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicVo {
    private String id;
    /**
     * topic creator
     */
    private User user;
    private TopicType tab;
    private String content;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date lastReplyAt;
    private Boolean good;
    private Boolean top;
    private Integer replyCount;
    private Integer visitCount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createTime;
    private List<ReplyVo> replies;

    public TopicVo(User user, Topic topic, boolean hasContent, List<Reply> replies) {
        this.id = topic.getId();
        User user1 = new User();
        user1.setAvatarUrl(user.getAvatarUrl());
        user1.setLoginname(user.getLoginname());
        this.user = user1;
        this.tab = topic.getTab();
        if (hasContent) {
            this.content = topic.getTopicContent().getContent();
        }
        this.title = topic.getTitle();
        this.lastReplyAt = topic.getLastReplyAt();
        this.good = topic.getGood()==1;
        this.top = topic.getTop()==1;
        this.replyCount = topic.getReplyCount();
        this.visitCount = topic.getVisitCount();
        this.createTime = topic.getCreateTime();
        List<ReplyVo> replyVos = new ArrayList<>();
        if (replies != null) {
            for (Reply reply : replies) {
                replyVos.add(new ReplyVo(reply));
            }
        }
        this.replies = replyVos;
    }

    public String getId() {
        return id;
    }

    public List<ReplyVo> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyVo> replies) {
        this.replies = replies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TopicType getTab() {
        return tab;
    }

    public void setTab(TopicType tab) {
        this.tab = tab;
    }

    public String getContent() {
        return content;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastReplyAt() {
        return lastReplyAt;
    }

    public void setLastReplyAt(Date lastReplyAt) {
        this.lastReplyAt = lastReplyAt;
    }


    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
