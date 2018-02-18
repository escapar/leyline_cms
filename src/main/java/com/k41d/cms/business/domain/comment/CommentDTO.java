package com.k41d.cms.business.domain.comment;

import java.time.ZonedDateTime;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonView;
import com.k41d.cms.business.domain.topic.TopicDTO;
import com.k41d.cms.business.domain.topic.TopicDetailDTO;
import com.k41d.cms.business.domain.user.UserDTO;
import com.k41d.cms.interfaces.view.CMSView;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Getter
@Setter
@Accessors(chain = true)

public class CommentDTO implements LeylineDTO {

    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private String title;

    @JsonView(CMSView.LIST.class)
    private String content;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.LIST.class)
    private UserDTO user;

    @JsonView(CMSView.LIST.class)
    private TopicDetailDTO topicDetail;

    @JsonView(CMSView.LIST.class)
    private TopicDTO topic;


}
