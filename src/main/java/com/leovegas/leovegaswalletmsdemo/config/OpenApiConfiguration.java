package com.leovegas.leovegaswalletmsdemo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${build.version}") String appVersion, @Value("${application.name}") String appName) {
        return new OpenAPI()
                .info(new Info()
                        .title("Application API of " + appName)
                        .version(appVersion)
                        .description("Wallet microservice for Leo Vegas Interview")
                        .contact(new Contact()
                                .name("Andrey Yelmanov")
                                .email("andrewyelmanov@gmail.com")
                        )
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Dev service"))
                );
    }
}
