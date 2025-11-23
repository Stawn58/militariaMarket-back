package com.militiariaapp.backend.product.model;

import com.militiariaapp.backend.gallery.model.Gallery;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private double price;
    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;
    @OneToMany(mappedBy = "product")
    private List<ProductImage> images;
}
