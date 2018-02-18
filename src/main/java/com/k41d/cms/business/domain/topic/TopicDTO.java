package com.k41d.cms.business.domain.topic;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonView;
import com.k41d.cms.business.domain.category.CategoryDTO;
import com.k41d.cms.business.domain.comment.CommentDTO;
import com.k41d.cms.interfaces.view.CMSView;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Getter
@Setter
@Accessors(chain = true)
public class TopicDTO implements LeylineDTO {
    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private String name;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.LIST.class)
    private boolean featured;

    @JsonView(CMSView.LIST.class)
    private CategoryDTO category;

    @JsonView(CMSView.LIST.class)
    private TopicDetailDTO latest;

    @JsonView(CMSView.LIST.class)
    private List<CommentDTO> comments;

    @JsonView(CMSView.LIST.class)
    private List<TopicDetailDTO> versions;

    @JsonView(CMSView.LIST.class)
    private List<TopicLikeDTO> likes;



}
