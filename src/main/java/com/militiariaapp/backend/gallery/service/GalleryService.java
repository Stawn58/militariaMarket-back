package com.militiariaapp.backend.gallery.service;

import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;

import java.util.UUID;

public interface GalleryService {

    void saveGallery(GalleryCreationView galleryCreationView);

    GallerySummaryView getGallery(UUID id);
}
