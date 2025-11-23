package com.militiariaapp.backend.product.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailView {
    private String name;
    private String description;
    private double price;
    private List<ProductImageDetailView> images;
}
