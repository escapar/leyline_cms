package org.escapar.cms.business.domain.topic;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonView;
import org.escapar.cms.business.domain.category.CategoryDTO;
import org.escapar.cms.business.domain.comment.CommentDTO;
import org.escapar.cms.business.domain.tag.TagDTO;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

import org.escapar.cms.business.domain.tag.TagDTO;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

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



}
