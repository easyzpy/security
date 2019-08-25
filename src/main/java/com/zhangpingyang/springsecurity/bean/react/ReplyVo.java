package com.zhangpingyang.springsecurity.bean.react;

import com.zhangpingyang.springsecurity.bean.UserVo;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.entity.react.Reply;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReplyVo {
    private String id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发表回复的用户
     */
    private UserVo user;
    /**
     * 回复的主题id
     */
    private TopicVo topic;
    /**
     * 回复内容
     */
    private String content;

    /**
     * 被回复的回复id
     */
    private String replyId;
    /**
     * 点赞的用户
     */
    private List<UserVo> ups;

    public ReplyVo() {
    }

    public ReplyVo(Reply reply) {
        this.id = reply.getId();
        this.createTime = reply.getCreateTime();
//        this.user = reply.getUser();
        if (reply.getUser() != null) {
            this.user = new UserVo(reply.getUser());
        }
//        this.topic = reply.getTopic();
        this.content = reply.getContent();
        this.replyId = reply.getReplyId();
        List<UserVo> ups = new ArrayList<>();
        if (reply.getUps() != null) {
            for (User up : reply.getUps()) {
                ups.add(new UserVo(up));
            }
        }
        this.ups = ups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public TopicVo getTopic() {
        return topic;
    }

    public void setTopic(TopicVo topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public List<UserVo> getUps() {
        return ups;
    }

    public void setUps(List<UserVo> ups) {
        this.ups = ups;
    }
}
