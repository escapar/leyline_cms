package com.k41d.cms.business.domain.topic;

import java.time.ZonedDateTime;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonView;
import com.k41d.cms.business.domain.topic.TopicDTO;
import com.k41d.cms.interfaces.view.CMSView;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Getter
@Setter
@Accessors(chain = true)
public class TopicDetailDTO implements LeylineDTO {
    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private String mainVersion;

    @JsonView(CMSView.LIST.class)
    private String subVersion;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime savedAt;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime publishedAt;

    @JsonView(CMSView.LIST.class)
    private boolean published;

    @JsonView(CMSView.LIST.class)
    private String title;

    @JsonView(CMSView.LIST.class)
    private String thumbnail;

    @JsonView(CMSView.DETAIL.class)
    private String content;

    @JsonView(CMSView.LIST.class)
    private TopicDTO topic;

    @JsonView(CMSView.LIST.class)
    private String summary;

}
