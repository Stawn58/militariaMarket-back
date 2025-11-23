package com.militiariaapp.backend.product.service.repository;

import com.militiariaapp.backend.product.model.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, UUID> {

    List<ProductImage> findByProductId(UUID productId);
}

