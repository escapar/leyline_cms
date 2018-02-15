package com.k41d.cms.business.domain.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.domain.topic.TopicDetail;
import com.k41d.cms.business.domain.user.User;

import org.joda.time.DateTime;

import com.k41d.leyline.framework.domain.LeylineDO;

@Entity
@Table(name="comment")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable,LeylineDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="created_at")
    private DateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="topic_detail_id")
    private TopicDetail topicDetail;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;

}
