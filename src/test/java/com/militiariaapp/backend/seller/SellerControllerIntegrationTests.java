package com.militiariaapp.backend.seller;

import com.militiariaapp.backend.user.MilitariaIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SellerControllerIntegrationTests extends MilitariaIntegrationTests {


    @Test
    void getSellerShouldReturnSeller() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/sellers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
