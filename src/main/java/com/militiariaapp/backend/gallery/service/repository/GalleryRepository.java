package com.militiariaapp.backend.gallery.service.repository;

import com.militiariaapp.backend.gallery.model.Gallery;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GalleryRepository extends CrudRepository<Gallery, UUID> {
}
