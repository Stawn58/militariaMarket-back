package com.militiariaapp.backend.gallery.web;

import com.militiariaapp.backend.common.exception.ResourceNotFoundException;
import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.GalleryService;
import com.militiariaapp.backend.product.model.ProductCreationView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/{sellerId}/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> saveProductGallery(@PathVariable UUID sellerId, @ModelAttribute ProductCreationView product) {
        return ResponseEntity.ok(galleryService.addProduct(sellerId, product));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> saveGallery(@RequestBody GalleryCreationView galleryCreationView, @PathVariable UUID id) {
        if (!id.equals(galleryCreationView.getSellerId())) {
            throw new IllegalArgumentException("Seller ID in path does not match Seller ID in request body");
        }

        galleryService.saveGallery(galleryCreationView);

        return ResponseEntity.ok().build();
    }
}
