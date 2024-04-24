package com.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        // Set the maximum allowed size (in bytes) for each individual file
        resolver.setMaxUploadSizePerFile(5242880); // 5MB
        // Set the maximum allowed size (in bytes) for the entire request
        resolver.setMaxUploadSize(20971520); // 20MB
        return resolver;
    }
}
