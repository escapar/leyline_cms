package com.k41d.cms.interfaces.dto;

import org.joda.time.DateTime;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class CommentDTO implements LeylineDTO {

    private long id;

    private String title;

    private String content;

    private DateTime createdAt;

    private UserDTO user;

    private TopicDetailDTO topicDetail;

    private TopicDTO topic;


}
