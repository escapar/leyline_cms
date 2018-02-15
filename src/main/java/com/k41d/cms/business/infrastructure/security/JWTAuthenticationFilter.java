package com.k41d.cms.business.infrastructure.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.vs.business.domain.worker.Worker;
import com.vs.business.service.WorkerService;

import com.k41d.leyline.framework.infrastructure.configurations.StatelessAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/**
 * Created by POJO on 6/15/16.
 */
@Component("JWTAuthenticationFilter")
public class JWTAuthenticationFilter extends StatelessAuthenticationFilter {
    public WorkerService userService;


    @Autowired
    public JWTAuthenticationFilter(WorkerService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) throws ServletException {
        Worker user = null;
        try {
            Claims c = JWTTokenUtils.parse(request);
            user = userService.getByClaims(c);
            //MDC.put("name", c.get("name"));
            return user;
        } catch (final Exception e) {
            e.printStackTrace();
            return user;
        }
    }
}
