package com.k41d.cms.business.domain.category;

import java.time.ZonedDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.k41d.cms.business.domain.commons.CategoryType;
import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.interfaces.view.CMSView;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;
import com.k41d.leyline.framework.interfaces.view.LeylineView;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryDTO implements LeylineDTO {

    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private String name;

    @JsonView(CMSView.LIST.class)
    private String alias;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.LIST.class)
    private CategoryType type;

    @JsonView(CMSView.LIST.class)
    private String reference;


}
