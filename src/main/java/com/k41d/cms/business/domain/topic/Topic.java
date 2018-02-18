package com.k41d.cms.business.domain.topic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.cms.business.domain.comment.Comment;


import com.k41d.cms.business.domain.tag.Tag;
import com.k41d.leyline.framework.domain.LeylineDO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@Entity
@Table(name="topic")
@NamedQuery(name="Topic.findAll", query="SELECT topic FROM Topic topic")
public class Topic implements Serializable,LeylineDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="featured")
    private boolean featured;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="latest_topic_detail_id")
    private TopicDetail latest;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name="topic_id")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name="topic_id")
    private List<TopicDetail> versions;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name="topic_id")
    private List<TopicLike> likes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="topic_tag",
            joinColumns={@JoinColumn(name="topic_id")},
            inverseJoinColumns={@JoinColumn(name="tag_id")})
    private List<Tag> tags;

}
