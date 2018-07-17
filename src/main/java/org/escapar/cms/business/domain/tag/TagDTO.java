package org.escapar.cms.business.domain.tag;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TagDTO implements LeylineDTO {

    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private String name;

    @JsonView(CMSView.DETAIL.class)
    private ZonedDateTime createdAt;


}
