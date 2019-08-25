package com.zhangpingyang.springsecurity.entity.react;

import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.enumeration.react.TopicType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/8/23 16:23
 */
@Entity
@Table(name = "tb_topic")
public class Topic {
    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;
    /**
     * topic creator
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TopicType tab;
    /*varchar 是可变长字符串，不预先分配存储空间，长度不要超过 5000，如果存储长
    度大于此值，定义字段类型为 text，独立出来一张表，用主键来对应，避免影响其它字段索
    引效率。*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TopicContent topicContent;

    @Column(length = 200)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date lastReplyAt;

    @Column
    private Integer good;
    @Column
    private Integer top;
    @Column
    private Integer replyCount;
    @Column
    private Integer visitCount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createTime;
    @Transient
    private List<Reply> replies;

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getId() {
        return id;
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

    public TopicContent getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(TopicContent topicContent) {
        this.topicContent = topicContent;
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

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
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

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", tab=" + tab +
                ", topicContent=" + topicContent +
                ", title='" + title + '\'' +
                ", lastReplyAt=" + lastReplyAt +
                ", good=" + good +
                ", top=" + top +
                ", replyCount=" + replyCount +
                ", visitCount=" + visitCount +
                ", createTime=" + createTime +
                '}';
    }
/*{
    "success":true,
    "data":[
        {
            "id":"5d5e9c9c421846662d98428e",
            "author_id":"5ce742f34036f24194cf5fa1",
            "tab":"ask",
            "content":"多列举几个 各有什么优势",
            "title":"node.js 有什么做优秀GUI界面的库？",
            "last_reply_at":"2019-08-23T01:20:15.579Z",
            "good":false,
            "top":false,
            "reply_count":2,
            "visit_count":174,
            "create_at":"2019-08-22T13:46:04.017Z",
            "author":{
                "loginname":"XiaoShouMr",
                "avatar_url":"https://avatars3.githubusercontent.com/u/50972319?v=4&s=120"
            }
        }
    ]
}*/
}
