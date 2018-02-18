package com.k41d.cms.business.domain.topic;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.k41d.cms.business.domain.user.User;


import com.k41d.leyline.framework.domain.LeylineDO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="topic_like")
@NamedQuery(name="TopicLike.findAll", query="SELECT tl FROM TopicLike tl")
public class TopicLike implements Serializable,LeylineDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="ip")
    private String ip;

    @Column(name="user_id")
    private User user;

    @Column(name="created_at")
    private ZonedDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;


}
