package com.k41d.cms.business.domain.topic;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
@Accessors(chain = true)
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
    private ZonedDateTime createdAt;

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
            joinColumns={@JoinColumn(name="topic_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="tag_id", referencedColumnName="id")})
    private List<Tag> tags;

    public void setComments(List<Comment> comments) {
        if(this.getComments()!=null) {
            this.getComments().clear();
        }else{
            this.comments = new ArrayList<>();
        }
        if(comments!=null) {
            this.getComments().addAll(comments);
        }
    }

    public void setVersions(List<TopicDetail> versions) {
        if(this.getVersions()!=null) {
            this.getVersions().clear();
        }else{
            this.versions = new ArrayList<>();
        }
        if(versions!=null) {
            this.getVersions().addAll(versions);
        }
    }


    public void setLikes(List<TopicLike> likes) {
        if(this.getLikes()!=null) {
            this.getLikes().clear();
        }else{
            this.likes = new ArrayList<>();
        }
        if(likes!=null) {
            this.getLikes().addAll(likes);
        }
    }
}
