package com.militiariaapp.backend.product.mapper;

import com.militiariaapp.backend.product.model.Product;
import com.militiariaapp.backend.product.model.ProductCreationView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "gallery", ignore = true)
    Product asProduct(ProductCreationView productCreationView);
}
