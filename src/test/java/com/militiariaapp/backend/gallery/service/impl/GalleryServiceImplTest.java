package com.militiariaapp.backend.gallery.service.impl;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.gallery.model.Gallery;
import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.repository.GalleryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GalleryServiceImplTest extends MilitariaUnitTests {

    @Mock
    private GalleryRepository repository;

    @InjectMocks
    private GalleryServiceImpl service;

    @Test
    void saveGallery_happyPath_savesGallery() {
        var view = new GalleryCreationView();
        view.setName("Test Gallery");
        view.setDescription("A description");

        when(repository.save(any(Gallery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveGallery(view);

        var captor = ArgumentCaptor.forClass(Gallery.class);
        verify(repository, times(1)).save(captor.capture());
        Gallery saved = captor.getValue();
        assertEquals("Test Gallery", saved.getName());
        assertEquals("A description", saved.getDescription());
    }

    @Test
    void saveGallery_nullView_throwsNpe_andDoesNotCallRepository() {
        assertThrows(NullPointerException.class, () -> service.saveGallery(null));

        verify(repository, never()).save(any());
    }

    @Test
    void getGallery_galleryExists_returnsSummaryView() {
        var id = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setName("Existing Gallery");
        gallery.setDescription("Existing description");
        gallery.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(gallery));

        GallerySummaryView result = service.getGallery(id);

        assertNotNull(result);
        assertEquals("Existing Gallery", result.getName());
        assertEquals("Existing description", result.getDescription());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getGallery_galleryNotFound_returnsNull() {
        var id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        GallerySummaryView result = service.getGallery(id);

        assertNull(result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void saveGallery_withEmptyStrings_savesEmptyStrings() {
        var view = new GalleryCreationView();
        view.setName("");
        view.setDescription("");

        when(repository.save(any(Gallery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveGallery(view);

        var captor = ArgumentCaptor.forClass(Gallery.class);
        verify(repository).save(captor.capture());
        Gallery saved = captor.getValue();
        assertNotNull(saved);
        assertEquals("", saved.getName());
        assertEquals("", saved.getDescription());
    }

    @Test
    void saveGallery_withNullFields_savesNulls() {
        var view = new GalleryCreationView();

        when(repository.save(any(Gallery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveGallery(view);

        var captor = ArgumentCaptor.forClass(Gallery.class);
        verify(repository).save(captor.capture());
        Gallery saved = captor.getValue();
        assertNotNull(saved);
        assertNull(saved.getName());
        assertNull(saved.getDescription());
    }

    @Test
    void saveGallery_withVeryLongStrings_preservesLength() {
        var longName = "x".repeat(5000);
        var longDesc = "d".repeat(8000);

        var view = new GalleryCreationView();
        view.setName(longName);
        view.setDescription(longDesc);

        when(repository.save(any(Gallery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveGallery(view);

        var captor = ArgumentCaptor.forClass(Gallery.class);
        verify(repository).save(captor.capture());
        Gallery saved = captor.getValue();
        assertEquals(5000, saved.getName().length());
        assertEquals(8000, saved.getDescription().length());
    }

    @Test
    void saveGallery_repositoryThrows_propagatesException() {
        var view = new GalleryCreationView();
        view.setName("Will Throw");

        when(repository.save(any(Gallery.class))).thenThrow(new RuntimeException("db down"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveGallery(view));
        assertEquals("db down", ex.getMessage());
        verify(repository, times(1)).save(any(Gallery.class));
    }

    @Test
    void getGallery_whenGalleryHasNullFields_returnsViewWithNulls() {
        var id = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(gallery));

        GallerySummaryView result = service.getGallery(id);

        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getDescription());
    }
}