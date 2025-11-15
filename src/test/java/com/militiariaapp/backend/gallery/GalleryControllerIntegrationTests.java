package com.militiariaapp.backend.gallery;

import com.militiariaapp.backend.MilitariaIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GalleryControllerIntegrationTests extends MilitariaIntegrationTests {

    @Test
    void getGalleryShouldReturnGallery() throws Exception {
        var id = UUID.fromString("a1b2c3d4-e5f6-4789-abcd-ef0123456789");
        var uriTemplate = "/galleries/" + id;

        mvc.perform(MockMvcRequestBuilders.get(uriTemplate))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Gallery"))
                .andExpect(jsonPath("$.description").value("A gallery for testing purposes."));
    }

    @Test
    void getGallery_notFound_returnsNotFound() throws Exception {
        var id = UUID.randomUUID();
        var uriTemplate = "/galleries/" + id;

        mvc.perform(MockMvcRequestBuilders.get(uriTemplate))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }
}
