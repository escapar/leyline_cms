package com.k41d.cms.business.domain.topic;

import java.time.ZonedDateTime;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonView;
import com.k41d.cms.business.domain.user.UserDTO;
import com.k41d.cms.interfaces.view.CMSView;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Getter
@Setter
@Accessors(chain = true)
public class TopicLikeDTO implements LeylineDTO {
    @JsonView(CMSView.LIST.class)
    private long id;

    private String ip;

    @JsonView(CMSView.LIST.class)
    private UserDTO user;

    private ZonedDateTime createdAt;

    private TopicDTO topic;


}
