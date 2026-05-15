package com.study.boot.react.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${file.upload.dir}")
    private String FILE_UPLOAD_DIR;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("/img/board/**")
                .addResourceLocations("file:///" + FILE_UPLOAD_DIR + "img/board/");
        resourceHandlerRegistry.addResourceHandler("/img/member/**")
                .addResourceLocations("file:///" + FILE_UPLOAD_DIR + "img/profile/**");
    }
}
