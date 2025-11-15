package com.militiariaapp.backend.gallery.model.view;

import lombok.Data;

import java.util.UUID;

@Data
public class GalleryCreationView {
    private UUID sellerId;
    private String name;
    private String description;
}
