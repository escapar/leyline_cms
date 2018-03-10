package com.k41d.cms.business.domain.topic;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="created_at")
    private ZonedDateTime createdAt;


}
