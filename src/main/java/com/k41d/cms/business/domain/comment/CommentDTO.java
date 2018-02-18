package com.k41d.cms.business.domain.comment;

import java.time.LocalDateTime;


import lombok.Data;

import com.k41d.cms.business.domain.topic.TopicDTO;
import com.k41d.cms.business.domain.topic.TopicDetailDTO;
import com.k41d.cms.business.domain.user.UserDTO;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class CommentDTO implements LeylineDTO {

    private long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private UserDTO user;

    private TopicDetailDTO topicDetail;

    private TopicDTO topic;


}
