package com.militiariaapp.backend.cloudinary.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class CloudinaryConfig {

    private final String cloudinaryUrl;

    @SuppressWarnings("unused")
    public CloudinaryConfig(@Value("${SPRING_CLOUDINARY_URL:cloudinary://default-value-url}") String cloudinaryUrl) {
        this.cloudinaryUrl = cloudinaryUrl;
    }

    @Bean
    @SuppressWarnings("unused")
    public Cloudinary cloudinary() {
        return new Cloudinary(cloudinaryUrl);
    }
}
