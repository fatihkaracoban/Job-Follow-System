package com.fatihkaracoban.job_follow_system.security;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Uploads klasörünün yolunu alıyoruz
        Path uploadDir = Paths.get("./uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Tarayıcı "/is-fotograflari/**" ile başlayan bir istek yaparsa, "uploads" klasörüne bak diyoruz.
        registry.addResourceHandler("/is-fotograflari/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}
