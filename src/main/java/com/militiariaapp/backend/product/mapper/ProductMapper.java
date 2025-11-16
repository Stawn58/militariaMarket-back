package com.militiariaapp.backend.product.mapper;

import com.militiariaapp.backend.product.model.Product;
import com.militiariaapp.backend.product.model.ProductCreationView;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    Product asProduct(ProductCreationView productCreationView);
}
