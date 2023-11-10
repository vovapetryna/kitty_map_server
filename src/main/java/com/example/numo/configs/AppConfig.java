package com.example.numo.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Primary
@Configuration
@EnableWebMvc
@Slf4j
public class AppConfig extends WebMvcConfigurationSupport {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info().title("UavSim Instructor service")
                .description("UavSim Instructor service")
                .version("Version 0.1"));
    }

}
