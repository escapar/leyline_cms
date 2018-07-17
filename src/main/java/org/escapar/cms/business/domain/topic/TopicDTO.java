package org.escapar.cms.business.domain.topic;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.escapar.cms.business.domain.category.CategoryDTO;
import org.escapar.cms.business.domain.comment.CommentDTO;
import org.escapar.cms.business.domain.tag.TagDTO;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TopicDTO implements LeylineDTO {
    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private long latestId;

    @JsonView(CMSView.LIST.class)
    private String name;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.LIST.class)
    private boolean featured;

    @JsonView(CMSView.LIST.class)
    private boolean multiLang;

    @JsonView(CMSView.LIST.class)
    private CategoryDTO category;

    @JsonView(CMSView.LIST.class)
    private TopicDetailDTO latestPublished;

    @JsonView(CMSView.ADMIN.class)
    private TopicDetailDTO latest;

    @JsonView(CMSView.DETAIL.class)
    private List<CommentDTO> comments;

    @JsonView(CMSView.LIST.class)
    private List<TagDTO> tags;

    @JsonView(CMSView.DETAIL.class)
    private List<TopicDetailDTO> versions;

    @JsonView(CMSView.LIST.class)
    private List<TopicLikeDTO> likes;

    @JsonView(CMSView.LIST.class)
    private String latestUnpublishedVersion;


    public TopicDTO setVersions(final List<TopicDetailDTO> versions) {
        this.versions = versions;
        this.getLatestUnpublishedVersion();
        return this;
    }

    public void getLatestUnpublishedVersion(){
        if(versions == null || versions.size() < 1) latestUnpublishedVersion = "";
        try {
            TopicDetailDTO t = versions.stream().filter(j->!j.isPublished()).max(Comparator.comparing(tt -> Integer.valueOf(tt.getMainVersion()) * 1000 + Integer.valueOf(tt.getSubVersion()))).get();
            latestUnpublishedVersion = t.getMainVersion() + "." + t.getSubVersion();
        }catch (Exception e){
            latestUnpublishedVersion = "";
        }
    }

}
