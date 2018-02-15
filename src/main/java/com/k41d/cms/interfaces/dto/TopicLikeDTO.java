package com.k41d.cms.interfaces.dto;

import org.joda.time.DateTime;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class TopicLikeDTO implements LeylineDTO {
    private long id;

    private String ip;

    private UserDTO user;

    private DateTime createdAt;

    private TopicDTO topic;


}
