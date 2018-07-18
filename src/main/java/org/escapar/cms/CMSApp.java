package org.escapar.cms;

import org.escapar.leyline.framework.LeylineApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@EnableJpaRepositories(basePackages = {"org.escapar.cms.business.service","org.escapar.cms.business.domain"})
@ComponentScan(basePackages = {"org.escapar.cms" })
@EntityScan(basePackages ={"org.escapar.cms.business","org.escapar.cms.infrastructure"})
@EnableAspectJAutoProxy
@EnableScheduling
@EnableConfigurationProperties
@EnableAdminServer
public class CMSApp extends LeylineApp {
    public static void main(String[] args) {
        SpringApplication.run(CMSApp.class, args);
    }
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}

