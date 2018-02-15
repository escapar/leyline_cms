package com.k41d.cms.business.domain.category;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;
import com.k41d.leyline.framework.interfaces.view.LeylineView;

import org.joda.time.DateTime;

@Data public class CategoryDTO implements LeylineDTO {

    @JsonView(LeylineView.LIST.class)
    private long id;

    @JsonView(LeylineView.LIST.class)
    private String name;

    @JsonView(LeylineView.LIST.class)
    private DateTime createdAt;


}
