package org.escapar.cms.business.domain.topic;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.*;

import org.escapar.cms.business.domain.user.User;


import org.escapar.leyline.framework.domain.LeylineDO;

import org.escapar.cms.business.domain.user.User;

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
