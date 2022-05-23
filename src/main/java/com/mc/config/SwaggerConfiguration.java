package com.mc.config;

import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Collections;
import java.util.List;

public class SwaggerConfiguration {

    private SwaggerConfiguration() {

    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    public static SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    public static ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "Header");
    }
}
