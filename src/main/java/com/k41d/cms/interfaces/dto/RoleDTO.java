package com.k41d.cms.interfaces.dto;

import java.util.Arrays;
import java.util.List;

import com.k41d.cms.business.infrastructure.security.ROLE_CONSTS;

import lombok.Data;
import com.k41d.leyline.framework.domain.user.LeylineUser;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class RoleDTO implements LeylineDTO{
    private String username;
    private String displayName;
    private String avatar;
    private List<String> roles;

    public static RoleDTO fromWorker(LeylineUser w){
        RoleDTO r = new RoleDTO();
        r.setAvatar("");
        r.setDisplayName(w.getName());
        r.setUsername(w.getUsername());
        r.setRoles(Arrays.asList(ROLE_CONSTS.getState(w.getRole())));
        return r;
    }
    public String getUsername() {
        return username;
    }

    public RoleDTO setUsername(final String username) {
        this.username = username;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RoleDTO setDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public RoleDTO setAvatar(final String avatar) {
        this.avatar = avatar;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public RoleDTO setRoles(final List<String> roles) {
        this.roles = roles;
        return this;
    }
}
