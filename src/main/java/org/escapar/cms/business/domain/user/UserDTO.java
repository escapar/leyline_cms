package org.escapar.cms.business.domain.user;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonView;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;

@Getter
@Setter
@Accessors(chain = true)
public class UserDTO implements LeylineDTO {

    @JsonView(CMSView.LIST.class)
    private long id;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime createdAt;

    @JsonView(CMSView.LIST.class)
    private ZonedDateTime birthday;

    @JsonView(CMSView.LIST.class)
    private String mail;

    @JsonView(CMSView.LIST.class)
    private String username;

    private String password;

    @JsonView(CMSView.LIST.class)
    private int role;


}
