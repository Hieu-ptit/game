package com.mc.bean;

import com.mc.config.SwaggerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        String groupName = "Game-Golf";
        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(new ApiInfo("Game golf Service Api Documentation",
                        "", "1.0", "", null, "",
                        "", new ArrayList<>()))
                .securityContexts(Collections.singletonList(SwaggerConfiguration.securityContext()))
                .securitySchemes(Collections.singletonList(SwaggerConfiguration.apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.controller"))
                .paths(PathSelectors.any())
                .build()
                .groupName(groupName);
    }
}
