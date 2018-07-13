package org.escapar.cms.business.domain.category;

import java.time.ZonedDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.escapar.cms.business.domain.commons.CategoryType;
import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;
import org.escapar.leyline.framework.interfaces.view.LeylineView;

import org.escapar.cms.business.domain.commons.CategoryType;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

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

    @JsonView(CMSView.DETAIL.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.DETAIL.class)
    private CategoryType type;

    @JsonView(CMSView.LIST.class)
    private String reference;


}
