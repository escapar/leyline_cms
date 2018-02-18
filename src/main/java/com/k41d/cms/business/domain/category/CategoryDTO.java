package com.k41d.cms.business.domain.category;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import com.k41d.cms.business.domain.commons.CategoryType;
import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;
import com.k41d.leyline.framework.interfaces.view.LeylineView;


@Data public class CategoryDTO implements LeylineDTO {

    @JsonView(LeylineView.LIST.class)
    private long id;

    @JsonView(LeylineView.LIST.class)
    private String name;

    @JsonView(LeylineView.LIST.class)
    private String alias;

    @JsonView(LeylineView.LIST.class)
    private LocalDateTime createdAt;

    @JsonView(LeylineView.LIST.class)
    private CategoryType type;

    @JsonView(LeylineView.LIST.class)
    private String reference;
}
