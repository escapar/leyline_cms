package org.escapar.leyline.framework.interfaces.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.infrastructure.security.JWTTokenUtils;
import org.escapar.cms.infrastructure.security.ROLE_CONSTS;

public class TokenDTO implements Serializable {
    private String token;
    private List<String> roles;


    public static TokenDTO fromUser(User u,JWTTokenUtils tokenUtils){
        return u == null ? null : new TokenDTO()
                .setToken(tokenUtils.sign(u))
                .setRoles(Arrays.asList(ROLE_CONSTS.getState(u.getRole())));
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
}
