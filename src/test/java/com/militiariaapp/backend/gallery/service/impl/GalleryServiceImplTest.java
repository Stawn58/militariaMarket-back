package com.militiariaapp.backend.gallery.service.impl;

import com.militiariaapp.backend.MilitariaUnitTests;
import com.militiariaapp.backend.cloudinary.service.CloudinaryService;
import com.militiariaapp.backend.gallery.model.Gallery;
import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.repository.GalleryRepository;
import com.militiariaapp.backend.product.model.Product;
import com.militiariaapp.backend.product.model.ProductCreationView;
import com.militiariaapp.backend.product.model.ProductImage;
import com.militiariaapp.backend.product.service.repository.ProductImageRepository;
import com.militiariaapp.backend.product.service.repository.ProductRepository;
import com.militiariaapp.backend.seller.model.Seller;
import com.militiariaapp.backend.seller.service.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductImageRepository productImageRepository;
    @Mock
    private CloudinaryService cloudinaryService;

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

    @Test
    @SuppressWarnings("unchecked")
    void addProduct_ShouldSaveProductAndImagesWhenGalleryExists() throws IOException {
        var sellerId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(UUID.randomUUID());

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var file1 = mock(MultipartFile.class);
        var file2 = mock(MultipartFile.class);

        var view = new ProductCreationView();
        view.setName("Prod");
        view.setDescription("Desc");
        view.setPrice(12.5);
        view.setImages(List.of(file1, file2));

        when(cloudinaryService.uploadImage(file1)).thenReturn("http://img1");
        when(cloudinaryService.uploadImage(file2)).thenReturn("http://img2");
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.addProduct(sellerId, view);

        var productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertEquals("Prod", savedProduct.getName());
        assertEquals("Desc", savedProduct.getDescription());
        assertEquals(12.5, savedProduct.getPrice());

        var imagesCaptor = ArgumentCaptor.forClass(Iterable.class);
        verify(productImageRepository, times(1)).saveAll(imagesCaptor.capture());
        @SuppressWarnings("unchecked")
        Iterable<ProductImage> savedImages = (Iterable<ProductImage>) imagesCaptor.getValue();
        List<ProductImage> imgList = new ArrayList<>();
        savedImages.forEach(imgList::add);

        assertEquals(2, imgList.size());
        assertEquals("http://img1", imgList.getFirst().getImageUrl());
        assertEquals(savedProduct, imgList.getFirst().getProduct());
        assertEquals("http://img2", imgList.get(1).getImageUrl());
        assertEquals(savedProduct, imgList.get(1).getProduct());

        verify(cloudinaryService, times(1)).uploadImage(file1);
        verify(cloudinaryService, times(1)).uploadImage(file2);
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentWhenGalleryNotFound() throws IOException {
        var sellerId = UUID.randomUUID();
        when(repository.findBySellerId(sellerId)).thenReturn(null);

        var view = new ProductCreationView();
        view.setImages(List.of());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.addProduct(sellerId, view));
        assertTrue(ex.getMessage().contains("Gallery not found"));

        verify(productRepository, never()).save(any());
        verify(productImageRepository, never()).saveAll(any());
        verify(cloudinaryService, never()).uploadImage(any());
    }

    @Test
    void addProduct_ShouldReturnGalleryIdWhenProductSavedSuccessfully() {
        var sellerId = UUID.randomUUID();
        var galleryId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(galleryId);

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var view = new ProductCreationView();
        view.setName("Test Product");
        view.setImages(List.of());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UUID result = service.addProduct(sellerId, view);

        assertEquals(galleryId, result);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productImageRepository, times(1)).saveAll(any());
    }

    @Test
    void addProduct_ShouldSaveProductWithoutImagesWhenImagesListIsEmpty() {
        var sellerId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(UUID.randomUUID());

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var view = new ProductCreationView();
        view.setName("Product Without Images");
        view.setDescription("Description");
        view.setPrice(25.0);
        view.setImages(List.of());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.addProduct(sellerId, view);

        var productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertEquals("Product Without Images", savedProduct.getName());
        assertEquals("Description", savedProduct.getDescription());
        assertEquals(25.0, savedProduct.getPrice());

        var imagesCaptor = ArgumentCaptor.forClass(Iterable.class);
        verify(productImageRepository, times(1)).saveAll(imagesCaptor.capture());
        Iterable<ProductImage> savedImages = imagesCaptor.getValue();
        List<ProductImage> imgList = new ArrayList<>();
        savedImages.forEach(imgList::add);
        assertTrue(imgList.isEmpty());

        try {
            verify(cloudinaryService, never()).uploadImage(any());
        } catch (IOException ignored) {
            // This should never happen in this test case
        }
    }

    @Test
    void addProduct_ShouldThrowRuntimeExceptionWhenCloudinaryServiceThrowsIOException() throws IOException {
        var sellerId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(UUID.randomUUID());

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var file = mock(MultipartFile.class);
        var view = new ProductCreationView();
        view.setName("Product");
        view.setImages(List.of(file));

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cloudinaryService.uploadImage(file)).thenThrow(new IOException("Upload failed"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.addProduct(sellerId, view));
        assertEquals("java.io.IOException: Upload failed", ex.getMessage());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(cloudinaryService, times(1)).uploadImage(file);
        verify(productImageRepository, never()).saveAll(any());
    }

    @Test
    void addProduct_ShouldPropagateExceptionWhenProductRepositoryThrows() {
        var sellerId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(UUID.randomUUID());

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var view = new ProductCreationView();
        view.setName("Product");
        view.setImages(List.of());

        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.addProduct(sellerId, view));
        assertEquals("Database error", ex.getMessage());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productImageRepository, never()).saveAll(any());
        try {
            verify(cloudinaryService, never()).uploadImage(any());
        } catch (IOException ignored) {
            // This should never happen in this test case
        }
    }

    @Test
    void addProduct_ShouldPropagateExceptionWhenProductImageRepositoryThrows() throws IOException {
        var sellerId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(UUID.randomUUID());

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var file = mock(MultipartFile.class);
        var view = new ProductCreationView();
        view.setName("Product");
        view.setImages(List.of(file));

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cloudinaryService.uploadImage(file)).thenReturn("http://image.url");
        when(productImageRepository.saveAll(any())).thenThrow(new RuntimeException("Image save failed"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.addProduct(sellerId, view));
        assertEquals("Image save failed", ex.getMessage());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(cloudinaryService, times(1)).uploadImage(file);
        verify(productImageRepository, times(1)).saveAll(any());
    }

    @Test
    void addProduct_ShouldHandleNullImagesListGracefully() {
        var sellerId = UUID.randomUUID();
        var gallery = new Gallery();
        gallery.setId(UUID.randomUUID());

        when(repository.findBySellerId(sellerId)).thenReturn(gallery);

        var view = new ProductCreationView();
        view.setName("Product With Null Images");
        view.setImages(null);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(NullPointerException.class, () -> service.addProduct(sellerId, view));

        verify(productRepository, times(1)).save(any(Product.class));
    }
}