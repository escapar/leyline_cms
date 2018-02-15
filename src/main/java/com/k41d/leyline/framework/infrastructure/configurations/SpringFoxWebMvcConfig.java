package com.k41d.leyline.framework.infrastructure.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by POJO on 6/27/16.
 */
@Configuration
public class SpringFoxWebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("/")
                .setCachePeriod(0);
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:9999","http://localhost:3000","http://116.196.123.67:666","http://vs.def.science")
//                .maxAge(450450450)
//                .allowedMethods("PUT", "DELETE", "POST", "GET", "OPTIONS")
//                //    .allowedHeaders()
//                .allowedHeaders("Origin",
//                        "Accept-Language",
//                        "Accept-Encoding",
//                        "X-Forwarded-For",
//                        "Connection",
//                        "Accept",
//                        "User-Agent",
//                        "Host",
//                        "Referer",
//                        "Cookie",
//                        "Content-Type",
//                        "Cache-Control",
//                        "If-Modified-Since")
//         .allowCredentials(true);
//    }


    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // you USUALLY want this
        config.setAllowedOrigins(Arrays.asList("http://localhost:9999","http://localhost:3000","http://116.196.123.67:666","http://vs.def.science"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof AbstractJackson2HttpMessageConverter) {
                AbstractJackson2HttpMessageConverter c = (AbstractJackson2HttpMessageConverter) converter;
                ObjectMapper objectMapper = c.getObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }
        }

        super.extendMessageConverters(converters);
    }


    @Bean
    public ResourceBundleViewResolver viewResolver(){
        ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
        resolver.setOrder(1);
        resolver.setBasename("views");
        return resolver;
    }

}
