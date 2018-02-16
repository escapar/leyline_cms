package com.k41d.leyline.framework.interfaces.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.k41d.cms.business.domain.user.User;
import com.k41d.cms.infrastructure.security.JWTTokenUtils;
import com.k41d.cms.infrastructure.security.ROLE_CONSTS;

public class TokenDTO implements Serializable {
    private String token;
    private List<String> roles;


    public static TokenDTO fromUser(User u){
        return u == null ? null : new TokenDTO()
                .setToken(JWTTokenUtils.sign(u))
                .setRoles(Arrays.asList(ROLE_CONSTS.getState(u.getRole())));
    }


    public String getToken() {
        return token;
    }

    public TokenDTO setToken(final String token) {
        this.token = token;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public TokenDTO setRoles(final List<String> roles) {
        this.roles = roles;
        return this;
    }
}
