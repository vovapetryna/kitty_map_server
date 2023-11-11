package com.example.kitty.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

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
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new GeoJsonModule())
                .build();
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

}
