package com.haohuo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by zhangpk
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(
        name = {"haohuo.config.enable.swagger"},
        havingValue = "true",
        matchIfMissing = true
)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        Docket api = new Docket(DocumentationType.SWAGGER_2);
        api.apiInfo(apiInfo());
        api.useDefaultResponseMessages(false);
        Set<String> produces = new HashSet<String>();
        produces.add("application/json");
        api.produces(produces);
        api.select().paths(PathSelectors.regex("/API/.*")).build();
        return api;
    }
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("MyTest API",
                "MyBatis-Plus Java API",
                "0.0.1",
                "zhangpk",
                "zhangpk",
                "Copyright © 2019 zhangpk Co. Ltd",
                "http://www.uacoding.cn"
        );
        return apiInfo;
    }
}
