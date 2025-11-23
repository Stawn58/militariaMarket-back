package com.militiariaapp.backend.gallery;

import com.militiariaapp.backend.MilitariaIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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

    @Test
    void saveProductGallery_ShouldAddProductToGallerySuccessfullyWhenDataIsValid() throws Exception {
        var sellerId = UUID.fromString("91e10690-867d-4bac-88ad-2ccac2f26392");
        var uriTemplate = "/galleries/" + sellerId + "/products";

        var image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "dummy-image-1".getBytes());
        var image2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "dummy-image-2".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart(uriTemplate)
                        .file(image1)
                        .file(image2)
                        .param("name", "New Product")
                        .param("description", "A newly added product.")
                        .param("price", "99.99")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void saveProductGallery_ShouldReturnBadRequestWhenGalleryNotFoundForSeller() throws Exception {
        var nonExistentSellerId = UUID.randomUUID();
        var uriTemplate = "/galleries/" + nonExistentSellerId + "/products";

        var image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "dummy-image-1".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart(uriTemplate)
                        .file(image1)
                        .param("name", "Product for Non-existent Gallery")
                        .param("description", "This should fail because gallery doesn't exist.")
                        .param("price", "49.99")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Gallery not found for seller: " + nonExistentSellerId));
    }
}
