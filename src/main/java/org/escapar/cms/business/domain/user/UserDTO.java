package org.escapar.cms.business.domain.user;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    @JsonView(CMSView.LIST.class)
    @Transient
    private List<String> roles;
}
