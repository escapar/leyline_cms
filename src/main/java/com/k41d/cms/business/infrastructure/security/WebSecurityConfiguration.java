package com.k41d.cms.business.infrastructure.security;

import com.vs.business.service.WorkerService;

import com.k41d.leyline.framework.infrastructure.configurations.LeylineWebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity


public class WebSecurityConfiguration extends LeylineWebSecurityConfiguration<WorkerService> {

    public WebSecurityConfiguration() {

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        //allow CORS option calls

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**")
                .permitAll()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(getUserDetailsService()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilter(new ExceptionTranslationFilter(new BasicAuthenticationEntryPoint()));

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**")
                .permitAll()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(getUserDetailsService()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilter(new ExceptionTranslationFilter(new BasicAuthenticationEntryPoint()));

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/**")
                .permitAll()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(getUserDetailsService()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilter(new ExceptionTranslationFilter(new BasicAuthenticationEntryPoint()));

//
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and().addFilterBefore(new JWTAuthenticationFilter(getUserDetailsService()),
//                UsernamePasswordAuthenticationFilter.class).formLogin().and().csrf().disable();

    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}


