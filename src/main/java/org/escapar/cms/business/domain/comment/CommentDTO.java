package org.escapar.cms.business.domain.comment;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import org.escapar.cms.business.domain.topic.TopicDTO;
import org.escapar.cms.business.domain.topic.TopicDetailDTO;
import org.escapar.cms.business.domain.user.UserDTO;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
