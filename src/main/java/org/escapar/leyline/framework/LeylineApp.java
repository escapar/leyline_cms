package org.escapar.leyline.framework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Created by bytenoob on 6/9/16.
 */

@EnableSpringDataWebSupport
@EnableConfigurationProperties

@EnableGlobalMethodSecurity(prePostEnabled = true)

@SpringBootApplication(exclude = {
        RepositoryRestMvcAutoConfiguration.class,
        JmxAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
})
@EntityScan("org.escapar.leyline.framework.domain")
@ComponentScan(basePackages = {"org.escapar.leyline.framework"},basePackageClasses = {Jsr310JpaConverters.class})

public abstract class LeylineApp {

}
