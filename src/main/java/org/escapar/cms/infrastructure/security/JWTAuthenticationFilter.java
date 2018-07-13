package org.escapar.cms.infrastructure.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.business.service.UserService;

import org.escapar.leyline.framework.infrastructure.configurations.StatelessAuthenticationFilter;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.business.service.UserService;
import org.escapar.leyline.framework.infrastructure.configurations.StatelessAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/**
 * Created by POJO on 6/15/16.
 */
@Component("JWTAuthenticationFilter")
public class JWTAuthenticationFilter extends StatelessAuthenticationFilter {
    public UserService userService;
    public JWTTokenUtils tokenUtils;

    @Autowired
    public JWTAuthenticationFilter(UserService userService) {
        this.userService = userService;this.tokenUtils = userService.getJwtTokenUtils();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) throws ServletException {
        User user = null;
        try {
            Claims c = tokenUtils.parse(request);
            user = userService.getByClaims(c);
            //MDC.put("name", c.get("name"));
            return user;
        } catch ( Exception e) {
            e.printStackTrace();
            return user;
        }
    }
}
