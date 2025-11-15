package com.militiariaapp.backend.gallery.web;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.common.exception.ResourceNotFoundException;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.GalleryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GalleryControllerUnitTest extends MilitariaUnitTests {

    @Mock
    private GalleryService galleryService;

    @InjectMocks
    private GalleryController controller;

    @Test
    void getGallery_galleryExists_returnsOk() {
        UUID id = UUID.randomUUID();
        GallerySummaryView view = new GallerySummaryView();
        view.setName("Test Gallery");
        view.setDescription("Description");

        when(galleryService.getGallery(any(UUID.class))).thenReturn(view);

        ResponseEntity<GallerySummaryView> response = controller.getGallery(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Gallery", response.getBody().getName());
        assertEquals("Description", response.getBody().getDescription());
        verify(galleryService, times(1)).getGallery(any(UUID.class));
    }

    @Test
    void getGallery_galleryNotFound_throwsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(galleryService.getGallery(any(UUID.class))).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> controller.getGallery(id));
        verify(galleryService, times(1)).getGallery(any(UUID.class));
    }
}
