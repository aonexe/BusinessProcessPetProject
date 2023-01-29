package com.vorsin.businessProcess.config;

import com.vorsin.businessProcess.dto.UserResponse;
import com.vorsin.businessProcess.models.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}