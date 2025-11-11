package com.militiariaapp.backend.gallery.model;

import com.militiariaapp.backend.product.model.Product;
import com.militiariaapp.backend.seller.model.Seller;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Gallery {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
