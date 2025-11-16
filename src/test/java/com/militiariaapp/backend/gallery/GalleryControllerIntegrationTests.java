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
    void getGallery_ShouldReturnGalleryWhenGalleryExists() throws Exception {
        var id = UUID.fromString("a1b2c3d4-e5f6-4789-abcd-ef0123456789");
        var uriTemplate = "/galleries/" + id;

        mvc.perform(MockMvcRequestBuilders.get(uriTemplate))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Gallery"))
                .andExpect(jsonPath("$.description").value("A gallery for testing purposes."));
    }

    @Test
    void saveGallery_ShouldSaveGallerySuccessfullyWhenDataIsValid() throws Exception {
        var sellerId = UUID.fromString("91e10690-867d-4bac-88ad-2ccac2f26392");
        var uriTemplate = "/galleries/" + sellerId;

        var galleryCreationJson = """
                {
                    "sellerId": "%s",
                    "name": "New Gallery",
                    "description": "A newly created gallery."
                }
                """.formatted(sellerId);

        mvc.perform(MockMvcRequestBuilders.post(uriTemplate)
                        .contentType("application/json")
                        .content(galleryCreationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void getGallery_ShouldReturnNotFoundWhenGalleryDoesNotExist() throws Exception {
        var id = UUID.randomUUID();
        var uriTemplate = "/galleries/" + id;

        mvc.perform(MockMvcRequestBuilders.get(uriTemplate))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    void saveGallery_ShouldReturnBadRequestWhenSellerIdDoesNotMatchPathId() throws Exception {
        var pathSellerId = UUID.fromString("91e10690-867d-4bac-88ad-2ccac2f26392");
        var bodySellerId = UUID.fromString("aaaaaaaa-bbbb-4ccc-8ddd-eeeeeeeeeeee");
        var uriTemplate = "/galleries/" + pathSellerId;

        var galleryCreationJson = """
                {
                    "sellerId": "%s",
                    "name": "Mismatch Gallery",
                    "description": "IDs do not match."
                }
                """.formatted(bodySellerId);

        mvc.perform(MockMvcRequestBuilders.post(uriTemplate)
                        .contentType("application/json")
                        .content(galleryCreationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveGallery_ShouldReturnBadRequestWhenSellerDoesNotExist() throws Exception {
        var nonExistingSellerId = UUID.randomUUID();
        var uriTemplate = "/galleries/" + nonExistingSellerId;

        var galleryCreationJson = """
                {
                    "sellerId": "%s",
                    "name": "Ghost Gallery",
                    "description": "Seller does not exist."
                }
                """.formatted(nonExistingSellerId);

        mvc.perform(MockMvcRequestBuilders.post(uriTemplate)
                        .contentType("application/json")
                        .content(galleryCreationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Seller not found: " + nonExistingSellerId));
    }
}
