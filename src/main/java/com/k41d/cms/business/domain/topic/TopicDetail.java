package com.k41d.cms.business.domain.topic;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.k41d.leyline.framework.domain.LeylineDO;

import lombok.Data;

@Data
@Entity
@Table(name="topic_detail")
@NamedQuery(name="TopicDetail.findAll", query="SELECT td FROM TopicDetail td")
public class TopicDetail implements Serializable,LeylineDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="main_version")
    private String mainVersion;

    @Column(name="sub_version")
    private String subVersion;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="saved_at")
    private LocalDateTime savedAt;

    @Column(name="published_at")
    private LocalDateTime publishedAt;

    @Column(name="published")
    private boolean published;

    @Column(name="title")
    private String title;

    @Column(name="thumbnail")
    private String thumbnail;

    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;

}
