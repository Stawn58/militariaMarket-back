package com.militiariaapp.backend.gallery.web;

import com.militiariaapp.backend.common.exception.ResourceNotFoundException;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/galleries")
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/{id}")
    public ResponseEntity<GallerySummaryView> getGallery(@PathVariable UUID id) {
        var gallerySummary = galleryService.getGallery(id);

        if (gallerySummary != null) {
            return ResponseEntity.ok(gallerySummary);
        } else {
            throw new ResourceNotFoundException("Gallery not found: " + id);
        }
    }
}
