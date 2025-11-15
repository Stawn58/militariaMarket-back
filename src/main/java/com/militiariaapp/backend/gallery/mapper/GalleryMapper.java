package com.militiariaapp.backend.gallery.mapper;

import com.militiariaapp.backend.gallery.model.Gallery;
import com.militiariaapp.backend.gallery.model.view.GallerySummaryView;
import org.mapstruct.Mapper;

@Mapper
public interface GalleryMapper {
    GallerySummaryView asSummaryView(Gallery gallery);
}
