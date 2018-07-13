package org.escapar.leyline.framework.infrastructure.configurations;

import org.escapar.leyline.framework.service.LeylineUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by POJO on 6/5/16.
 */
public abstract class LeylineWebSecurityConfiguration<S extends LeylineUserDetailsService> extends WebSecurityConfigurerAdapter {

    @Autowired
    private S userDetailsService;

    public LeylineWebSecurityConfiguration() {
        super(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    public S getUserDetailsService() {
        return userDetailsService;
    }

}
