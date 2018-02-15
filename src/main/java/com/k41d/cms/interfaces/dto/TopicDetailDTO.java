package com.k41d.cms.interfaces.dto;

import org.joda.time.DateTime;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class TopicDetailDTO implements LeylineDTO {
    private long id;

    private String mainVersion;

    private String subVersion;

    private DateTime createdAt;

    private DateTime savedAt;

    private DateTime publishedAt;

    private boolean published;

    private String title;

    private String thumbnail;

    private String content;

    private TopicDTO topic;
}
