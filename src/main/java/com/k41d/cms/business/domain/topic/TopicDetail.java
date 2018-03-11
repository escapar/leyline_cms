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

import com.k41d.cms.business.domain.commons.VersionUtil;
import com.k41d.leyline.framework.domain.LeylineDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
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
    private ZonedDateTime createdAt;

    @Column(name="published_at")
    private ZonedDateTime publishedAt;

    @Column(name="published")
    private boolean published;

    @Column(name="title")
    private String title;

    @Column(name="thumbnail")
    private String thumbnail;

    @Column(name="content")
    private String content;

    @Column(name="summary")
    private String summary;
//
//    @ManyToOne
//    @JoinColumn(name="topic_id")
//    private Topic topic;

    public TopicDetail fillInVersion(){
        // to ensure we've got version for every instance
        if(mainVersion == null || mainVersion.isEmpty()){
            mainVersion = "0";
        }
        if(subVersion == null || subVersion.isEmpty()){
            subVersion = "0";
        }
        return this;
    }

    public TopicDetail upgradeSubVersion(String previous){
        return setSubVersion(VersionUtil.nextSubVersion(previous));
    }

    public TopicDetail upgradeMainVersion(String previous){
        return setMainVersion(VersionUtil.nextMainVersion(previous)).setSubVersion("0");
    }

    public String getVersionString(){
        this.fillInVersion();
        return mainVersion + '.' + subVersion;
    }

    public boolean contentEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;

        TopicDetail that = (TopicDetail) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (thumbnail != null ? !thumbnail.equals(that.thumbnail) : that.thumbnail != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return summary != null ? summary.equals(that.summary) : that.summary == null;
    }

}
