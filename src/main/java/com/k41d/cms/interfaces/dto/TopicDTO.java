package com.k41d.cms.interfaces.dto;

import java.util.List;

import org.joda.time.DateTime;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class TopicDTO implements LeylineDTO {
    private long id;

    private String name;

    private DateTime createdAt;

    private boolean featured;

    private CategoryDTO category;

    private TopicDetailDTO lastest;

    private List<CommentDTO> comments;

    private List<TopicDetailDTO> versions;

    private List<TopicLikeDTO> likes;



}
