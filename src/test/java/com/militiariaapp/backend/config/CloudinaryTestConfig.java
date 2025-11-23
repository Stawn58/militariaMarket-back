package com.militiariaapp.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.Url;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class CloudinaryTestConfig {

    @Bean
    @Primary
    public Cloudinary cloudinary() throws IOException {
        var mockedCloudinary = mock(Cloudinary.class);
        var mockedUploader = mock(Uploader.class);
        var mockedUrl = mock(Url.class);

        when(mockedCloudinary.uploader()).thenReturn(mockedUploader);

        var mockResponse = Map.of(
                "public_id", "test_image_123",
                "version", "1234567890",
                "signature", "abc123def456",
                "width", 300,
                "height", 200,
                "format", "jpg",
                "resource_type", "image",
                "secure_url", "https://res.cloudinary.com/demo/image/upload/test_image_123.jpg",
                "url", "http://res.cloudinary.com/demo/image/upload/test_image_123.jpg"
        );

        // Mock upload with byte[] and Map - this is what CloudinaryService.uploadImage() calls
        when(mockedUploader.upload(any(byte[].class), any(Map.class))).thenReturn(mockResponse);

        // Mock other upload overloads just in case
        when(mockedUploader.upload(any(java.io.File.class), any(Map.class))).thenReturn(mockResponse);
        when(mockedUploader.upload(any(Object.class), any(Map.class))).thenReturn(mockResponse);

        when(mockedCloudinary.url()).thenReturn(mockedUrl);
        // Mock generate method - this is what CloudinaryService.uploadImage() calls after upload
        when(mockedUrl.generate(any(String.class))).thenReturn("https://res.cloudinary.com/demo/image/upload/test_image_123.jpg");

        return mockedCloudinary;
    }
}
