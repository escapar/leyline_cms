package com.k41d.cms.interfaces.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
