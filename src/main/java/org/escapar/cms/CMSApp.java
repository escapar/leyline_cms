package org.escapar.cms;

import org.escapar.leyline.framework.LeylineApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.ResponseMessageBuilder;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.schema.WildcardType;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.UiConfiguration;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import static springfox.documentation.schema.AlternateTypeRules.newRule;


@EnableJpaRepositories(basePackages = {"org.escapar.cms.business.service","org.escapar.cms.business.domain"})
//@EnableSwagger2
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
//
//    @Autowired
//    private TypeResolver typeResolver;
//
//    @Bean
//    public Docket appAPI() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                //.pathMapping("/")
//                .directModelSubstitute(LocalDate.class,
//                        String.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .alternateTypeRules(
//                        newRule(typeResolver.resolve(DeferredResult.class,
//                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
//                                typeResolver.resolve(WildcardType.class)))
//                .useDefaultResponseMessages(false)
////                .globalResponseMessage(RequestMethod.GET,
////                        newArrayList(new ResponseMessageBuilder()
////                                .code(500)
////                                .message("500 message")
////                                .responseModel(new ModelRef("Error"))
////                                .build()))
//                //.securitySchemes(newArrayList(apiKey()))
//                //.securityContexts(newArrayList(securityContext()))
//                .enableUrlTemplating(true)
////                .globalOperationParameters(
////                        newArrayList(new ParameterBuilder()
////                                .name("someGlobalParameter")
////                                .description("Description of someGlobalParameter")
////                                .modelRef(new ModelRef("string"))
////                                .parameterType("query")
////                                .required(true)
////                                .build()))
//                //.tags(new Tag("Pet Service", "All apis relating to pets"))
//                //.additionalModels(typeResolver.resolve(AdditionalModel.class))
//                ;
//    }
//
//    @Bean
//    UiConfiguration uiConfig() {
//        return new UiConfiguration(
//                "validatorUrl",// url
//                "none",       // docExpansion          => none | list
//                "alpha",      // apiSorter             => alpha
//                "schema",     // defaultModelRendering => schema
//                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
//                false,        // enableJsonEditor      => true | false
//                true,         // showRequestHeaders    => true | false
//                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
//    }
}
