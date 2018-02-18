package com.k41d.cms.business.domain.topic;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

import com.k41d.cms.business.domain.category.CategoryDTO;
import com.k41d.cms.business.domain.comment.CommentDTO;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class TopicDTO implements LeylineDTO {
    private long id;

    private String name;

    private LocalDateTime createdAt;

    private boolean featured;

    private CategoryDTO category;

    private TopicDetailDTO latest;

    private List<CommentDTO> comments;

    private List<TopicDetailDTO> versions;

    private List<TopicLikeDTO> likes;



}
