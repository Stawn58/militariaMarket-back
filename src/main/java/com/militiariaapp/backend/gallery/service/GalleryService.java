package com.militiariaapp.backend.gallery.service;

import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.product.model.ProductCreationView;

import java.util.UUID;

public interface GalleryService {

    void saveGallery(GalleryCreationView galleryCreationView);

    GallerySummaryView getGallery(UUID id);

    void addProduct(UUID sellerId, ProductCreationView productCreationView);
}
