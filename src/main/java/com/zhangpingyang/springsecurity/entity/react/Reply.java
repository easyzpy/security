package com.zhangpingyang.springsecurity.entity.react;

import com.zhangpingyang.springsecurity.entity.User;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 回复
 */
@Entity
@Table(name = "tb_reply")
public class Reply {
    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 发表回复的用户
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private User user;
    /**
     * 回复的主题id
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Topic topic;
    /**
     * 回复内容
     */
    @Column(length = 5000)
    private String content;

    /**
     * 被回复的回复id
     */
    @Column(length = 40)
    private String replyId;
    /**
     * 点赞的用户
     */
//    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
//    private List<User> users;
    @ManyToMany
    @JoinTable(
            name = "reply_user"
            , joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")}
    )
    private List<User> ups;

    public String getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<User> getUps() {
        return ups;
    }

    public void setUps(List<User> ups) {
        this.ups = ups;
    }
}
