package com.militiariaapp.backend.gallery.service.impl;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.gallery.model.Gallery;
import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.repository.GalleryRepository;
import com.militiariaapp.backend.seller.model.Seller;
import com.militiariaapp.backend.seller.service.repository.SellerRepository;
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
    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private GalleryServiceImpl service;

    @Test
    void saveGallery_ShouldSaveGalleryWhenViewIsValid() {
        var sellerId = UUID.randomUUID();
        var seller = new Seller();
        seller.setId(sellerId);

        var view = new GalleryCreationView();
        view.setSellerId(sellerId);
        view.setName("Test Gallery");
        view.setDescription("A description");

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(repository.save(any(Gallery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveGallery(view);

        var captor = ArgumentCaptor.forClass(Gallery.class);
        verify(repository, times(1)).save(captor.capture());
        Gallery saved = captor.getValue();
        assertEquals("Test Gallery", saved.getName());
        assertEquals("A description", saved.getDescription());
        assertEquals(seller, saved.getSeller());
    }

    @Test
    void saveGallery_ShouldThrowIllegalArgumentExceptionWhenSellerNotFound() {
        var sellerId = UUID.randomUUID();
        var view = new GalleryCreationView();
        view.setSellerId(sellerId);
        view.setName("Test Gallery");

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.saveGallery(view));
        assertTrue(ex.getMessage().contains("Seller not found"));

        verify(repository, never()).save(any());
    }

    @Test
    void getGallery_ShouldReturnSummaryViewWhenGalleryExists() {
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
    void getGallery_ShouldReturnNullWhenGalleryNotFound() {
        var id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        GallerySummaryView result = service.getGallery(id);

        assertNull(result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void saveGallery_ShouldSaveEmptyStringsWhenFieldsAreEmpty() {
        var sellerId = UUID.randomUUID();
        var seller = new com.militiariaapp.backend.seller.model.Seller();
        seller.setId(sellerId);

        var view = new GalleryCreationView();
        view.setSellerId(sellerId);
        view.setName("");
        view.setDescription("");

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
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
    void saveGallery_ShouldSaveNullsWhenFieldsAreNull() {
        var sellerId = UUID.randomUUID();
        var seller = new com.militiariaapp.backend.seller.model.Seller();
        seller.setId(sellerId);

        var view = new GalleryCreationView();
        view.setSellerId(sellerId);

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
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
    void saveGallery_ShouldPreserveLengthWhenStringsAreVeryLong() {
        var sellerId = UUID.randomUUID();
        var seller = new com.militiariaapp.backend.seller.model.Seller();
        seller.setId(sellerId);

        var longName = "x".repeat(5000);
        var longDesc = "d".repeat(8000);

        var view = new GalleryCreationView();
        view.setSellerId(sellerId);
        view.setName(longName);
        view.setDescription(longDesc);

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(repository.save(any(Gallery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveGallery(view);

        var captor = ArgumentCaptor.forClass(Gallery.class);
        verify(repository).save(captor.capture());
        Gallery saved = captor.getValue();
        assertEquals(5000, saved.getName().length());
        assertEquals(8000, saved.getDescription().length());
    }

    @Test
    void saveGallery_ShouldPropagateExceptionWhenRepositoryThrows() {
        var sellerId = UUID.randomUUID();
        var seller = new com.militiariaapp.backend.seller.model.Seller();
        seller.setId(sellerId);

        var view = new GalleryCreationView();
        view.setSellerId(sellerId);
        view.setName("Will Throw");

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(repository.save(any(Gallery.class))).thenThrow(new RuntimeException("db down"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveGallery(view));
        assertEquals("db down", ex.getMessage());
        verify(repository, times(1)).save(any(Gallery.class));
    }

    @Test
    void getGallery_ShouldReturnViewWithNullsWhenGalleryHasNullFields() {
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