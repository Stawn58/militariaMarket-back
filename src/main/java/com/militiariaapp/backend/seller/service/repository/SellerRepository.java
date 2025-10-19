package com.militiariaapp.backend.seller.service.repository;

import com.militiariaapp.backend.seller.model.Seller;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SellerRepository extends CrudRepository<Seller, UUID> {
}
