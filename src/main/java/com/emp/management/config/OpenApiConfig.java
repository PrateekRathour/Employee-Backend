package com.emp.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management System API")
                        .description("Production-grade RESTful APIs for Employee Onboarding, Profile Management, Department Search, and Analytics")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("DevOps & Platform Engineering Team")
                                .email("devops@empmanagement.io"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server"),
                        new Server().url("https://api.empmanagement.io").description("AWS Cloud Staging Server")
                ));
    }
}
