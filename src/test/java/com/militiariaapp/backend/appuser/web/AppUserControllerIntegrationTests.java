package com.militiariaapp.backend.appuser.web;

import com.militiariaapp.backend.MilitariaIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AppUserControllerIntegrationTests extends MilitariaIntegrationTests {

    @Test
    void createAppUser_ShouldReturnOkWithUUIDWhenRequestIsValid() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@example.com\"}";

        mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$", matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")));
    }

    @Test
    void createAppUser_ShouldReturnOkWhenDataIsValid() throws Exception {
        String json = "{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"email\":\"jane.smith@test.com\"}";

        mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void createAppUser_ShouldReturnBadRequestWhenRequestBodyMissing() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAppUser_ShouldReturnOkWithDefaultsWhenJSONIsEmpty() throws Exception {
        String json = "{}";

        mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")));
    }

    @Test
    void createAppUser_ShouldReturnBadRequestWhenJSONIsInvalid() throws Exception {
        String json = "{invalid json";

        mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAppUser_ShouldReturnDifferentUUIDsWhenMultipleRequests() throws Exception {
        String json1 = "{\"firstName\":\"User1\",\"lastName\":\"One\",\"email\":\"user1@example.com\"}";
        String json2 = "{\"firstName\":\"User2\",\"lastName\":\"Two\",\"email\":\"user2@example.com\"}";

        String uuid1 = mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String uuid2 = mvc.perform(MockMvcRequestBuilders.post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assert !uuid1.equals(uuid2) : "Generated UUIDs should be different";
    }
}
