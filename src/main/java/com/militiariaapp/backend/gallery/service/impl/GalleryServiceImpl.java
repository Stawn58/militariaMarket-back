package com.militiariaapp.backend.gallery.service.impl;

import com.militiariaapp.backend.gallery.mapper.GalleryMapper;
import com.militiariaapp.backend.gallery.model.Gallery;
import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.GalleryService;
import com.militiariaapp.backend.gallery.service.repository.GalleryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository repository;

    @Override
    @Transactional
    public void saveGallery(GalleryCreationView galleryCreationView) {
        repository.save(asGallery(galleryCreationView));
    }


    @Override
    public GallerySummaryView getGallery(UUID id) {
        var mapper = Mappers.getMapper(GalleryMapper.class);
        return repository.findById(id)
                .map(mapper::asSummaryView)
                .orElse(null);
    }

    private Gallery asGallery(GalleryCreationView galleryCreationView) {
        var gallery = new Gallery();
        gallery.setName(galleryCreationView.getName());
        gallery.setDescription(galleryCreationView.getDescription());

        return gallery;
    }
}
