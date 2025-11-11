package com.militiariaapp.backend.appuser.web;

import com.militiariaapp.backend.appuser.MilitariaIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppUserControllerIntegrationTests extends MilitariaIntegrationTests {

    @Test
    void createAppUser_shouldReturnOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
