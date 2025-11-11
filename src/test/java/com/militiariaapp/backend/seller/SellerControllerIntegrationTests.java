package com.militiariaapp.backend.seller;

import com.militiariaapp.backend.appuser.MilitariaIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SellerControllerIntegrationTests extends MilitariaIntegrationTests {


    @Test
    void getSellerShouldReturnSeller() throws Exception {
        var id = UUID.fromString("91e10690-867d-4bac-88ad-2ccac2f26392");
        var uriTemplate = "/sellers/" + id;

        mvc.perform(MockMvcRequestBuilders.get(uriTemplate))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Stardust Crusaders"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));
    }

    @Test
    void saveSeller_shouldReturnOk() throws Exception {
        String sellerJson = """
                {
                    "companyName": "New Seller Company",
                    "phoneNumber": "5555555555"
                }
                """;

        mvc.perform(MockMvcRequestBuilders.post("/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sellerJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
