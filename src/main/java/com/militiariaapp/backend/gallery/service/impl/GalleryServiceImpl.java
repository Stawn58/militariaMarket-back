package com.militiariaapp.backend.gallery.service.impl;

import com.militiariaapp.backend.cloudinary.service.CloudinaryService;
import com.militiariaapp.backend.gallery.mapper.GalleryMapper;
import com.militiariaapp.backend.gallery.model.Gallery;
import com.militiariaapp.backend.gallery.model.view.GalleryCreationView;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import com.militiariaapp.backend.gallery.service.GalleryService;
import com.militiariaapp.backend.gallery.service.repository.GalleryRepository;
import com.militiariaapp.backend.product.mapper.ProductMapper;
import com.militiariaapp.backend.product.model.Product;
import com.militiariaapp.backend.product.model.ProductCreationView;
import com.militiariaapp.backend.product.model.ProductImage;
import com.militiariaapp.backend.product.service.repository.ProductImageRepository;
import com.militiariaapp.backend.product.service.repository.ProductRepository;
import com.militiariaapp.backend.seller.model.Seller;
import com.militiariaapp.backend.seller.service.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository repository;
    private final SellerRepository sellerRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

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

    @Override
    @Transactional
    public UUID addProduct(UUID sellerId, ProductCreationView productCreationView) {
        var gallery = repository.findBySellerId(sellerId);
        if (gallery != null) {
            var productMapper = Mappers.getMapper(ProductMapper.class);

            var product = productMapper.asProduct(productCreationView);
            product.setGallery(gallery);

            var savedProduct = productRepository.save(product);

            var images = productCreationView.getImages();
            var productImages = asProductImages(savedProduct, images);

            productImageRepository.saveAll(productImages);

            return gallery.getId();
        }

        throw new IllegalArgumentException("Gallery not found for seller: " + sellerId);
    }

    private List<ProductImage> asProductImages(Product product, List<MultipartFile> images) {
        var productImages = new ArrayList<ProductImage>();
        if (!images.isEmpty()) {
            images.forEach(multipartFile -> {
                try {
                    var imageUrl = cloudinaryService.uploadImage(multipartFile);
                    productImages.add(asProductImage(product, imageUrl));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return productImages;
    }

    private ProductImage asProductImage(Product product, String imageUrl) {
        ProductImage productImage = new ProductImage();
        productImage.setImageUrl(imageUrl);
        productImage.setProduct(product);

        return productImage;
    }

    private Gallery asGallery(GalleryCreationView galleryCreationView) {
        Gallery gallery = new Gallery();
        gallery.setName(galleryCreationView.getName());
        gallery.setDescription(galleryCreationView.getDescription());

        var sellerId = galleryCreationView.getSellerId();

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found: " + sellerId));

        gallery.setSeller(seller);

        return gallery;
    }
}
