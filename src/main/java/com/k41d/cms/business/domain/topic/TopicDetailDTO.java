package com.k41d.cms.business.domain.topic;

import java.time.LocalDateTime;


import lombok.Data;

import com.k41d.cms.business.domain.topic.TopicDTO;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class TopicDetailDTO implements LeylineDTO {
    private long id;

    private String mainVersion;

    private String subVersion;

    private LocalDateTime createdAt;

    private LocalDateTime savedAt;

    private LocalDateTime publishedAt;

    private boolean published;

    private String title;

    private String thumbnail;

    private String content;

    private TopicDTO topic;
}
