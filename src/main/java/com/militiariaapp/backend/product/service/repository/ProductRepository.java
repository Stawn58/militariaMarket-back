package com.militiariaapp.backend.product.service.repository;

import com.militiariaapp.backend.product.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    List<Product> findByGalleryId(UUID galleryId);
}

