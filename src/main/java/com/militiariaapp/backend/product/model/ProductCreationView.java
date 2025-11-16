package com.militiariaapp.backend.product.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductCreationView {
    private String name;
    private String description;
    private double price;
    private List<MultipartFile> images;
}
