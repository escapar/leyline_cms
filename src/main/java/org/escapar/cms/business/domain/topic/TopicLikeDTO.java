package org.escapar.cms.business.domain.topic;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import org.escapar.cms.business.domain.user.UserDTO;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
