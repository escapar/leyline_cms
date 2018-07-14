package org.escapar.leyline.framework.interfaces.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.infrastructure.security.JWTTokenUtils;
import org.escapar.cms.infrastructure.security.ROLE_CONSTS;

public class TokenDTO implements Serializable {
    private String name;
    private String avatar;
    private String token;
    private List<String> roles;


    public static TokenDTO fromUser(User u,JWTTokenUtils tokenUtils){

        return u == null ? null : new TokenDTO()
                .setToken(tokenUtils.sign(u))
                .setRoles(Arrays.asList(ROLE_CONSTS.getState(u.getRole())))
                .setAvatar(u.getAvatar())
                .setName(u.getName());
    }


    public String getToken() {
        return token;
    }

    public TokenDTO setToken( String token) {
        this.token = token;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public TokenDTO setRoles( List<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getName() {
        return name;
    }

    public TokenDTO setName(final String name) {
        this.name = name;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public TokenDTO setAvatar(final String avatar) {
        this.avatar = avatar;
        return this;
    }
}
