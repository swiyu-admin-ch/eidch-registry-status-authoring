/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.infrastructure.web.config;

import ch.admin.bj.swiyu.registry.status.authoring.Application;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI openApi() {
        var openApi = new OpenAPI()
            .info(
                new Info()
                    .title("Status Registry Authoring Service")
                    .description("IF-006 - APIs for the Status Registry Authoring Services")
                    .version(buildProperties.getVersion())
            )
            .addSecurityItem(new SecurityRequirement().addList("OIDC")) // OIDC schema is provided by jeap's SwaggerOauthConfiguration
            .components(new Components());
        addBearerAuthAsWorkaroundForOpenApiCodegen(openApi);
        return openApi;
    }

    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder().group("API").packagesToScan(Application.class.getPackageName()).build();
    }

    /**
     * Workaround for <a href="https://github.com/OpenAPITools/openapi-generator/issues/19168">Github issue 19168</a>
     * when using generated api-doc with openapi codegen.
     */
    private void addBearerAuthAsWorkaroundForOpenApiCodegen(OpenAPI openApi) {
        var name = "bearer-jwt";
        openApi.getSecurity().getFirst().addList(name);
        openApi
            .getComponents()
            .addSecuritySchemes(
                name,
                new SecurityScheme()
                    .name("bearerAuth")
                    .description("JWT authentication")
                    .bearerFormat("jwt")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .in(SecurityScheme.In.HEADER)
            );
    }
}
