package org.escapar.cms.business.service;

import java.util.Collection;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.business.domain.user.UserRepo;
import org.escapar.cms.infrastructure.security.JWTTokenUtils;
import org.escapar.leyline.framework.service.LeylineUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;

@Service
public class UserService extends LeylineUserDetailsService<UserRepo,User> {
    private JWTTokenUtils jwtTokenUtils;

    public JWTTokenUtils getJwtTokenUtils() {
        return jwtTokenUtils;
    }

    @Autowired
    public UserService setJwtTokenUtils(final JWTTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
        return this;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User checkAndGet(String username, String password) {
        return getRepo().checkAndGet(username, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getRole(User user) {
        return User.getRole(user);
    }

    public User getByClaims(Claims c) throws Exception {
        return c == null ? null : getRepo().findById(Long.valueOf((Integer)c.get("id"))).orElse(null);
    }
}
