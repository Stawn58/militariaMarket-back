package com.militiariaapp.backend.cloudinary.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CloudinaryService {

    private final static String ALLOWED_FORMAT = "(?i).*\\.(jpg|png|jpeg)$";
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        if (!isAllowedFormat(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File format not allowed: " + file.getOriginalFilename());
        }

        var fileName = file.getName();
        cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", fileName));

        return cloudinary.url().generate(fileName);
    }

    public boolean isAllowedFormat(String fileName) {
        var matcher = Pattern.compile(ALLOWED_FORMAT, Pattern.CASE_INSENSITIVE).matcher(fileName);

        return matcher.matches();
    }
}
