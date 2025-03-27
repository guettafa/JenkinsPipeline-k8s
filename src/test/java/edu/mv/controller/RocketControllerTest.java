package edu.mv.controller;

import edu.mv.db.models.Rocket;
import edu.mv.mapping.RocketMapperImpl;
import edu.mv.models.RocketDTO;
import edu.mv.repository.RocketRepository;
import edu.mv.service.RocketService;
import edu.mv.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RocketControllerTest {

    @InjectMocks
    private RocketController rocketController;

    @Mock
    private RocketService rocketService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RocketRepository rocketRepository;

    private RocketDTO rocketDTO;

    @BeforeEach
    void setUp() {
        rocketDTO = RocketMapperImpl.INSTANCE.RocketToRocketDTO(
                rocketRepository.save(new Rocket(1,"SpecialRocket", "SorteSpecial"))
        );
    }

    /**
     * Test Insert a new rocket
     * @throws Exception
     */
    @Test
    void postRocket() throws Exception {
        when(rocketController.saveRocket(rocketDTO))
                .thenReturn(rocketDTO);

        mockMvc.perform(
                post("/rocket/").content(Utils.jsonRocket(rocketDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test getting an existing rocket with its id
     * @throws Exception
     */
    @Test
    void getRocketById_found() throws Exception {
        rocketRepository.save(new Rocket(1,"SpecialRocket", "SorteSpecial"));

        MvcResult result = mockMvc.perform(
                get("/rocket/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertEquals(jsonResponse, Utils.jsonRocket(rocketDTO));
    }

    /**
     * Test getting a nonexistent rocket with its id
     * @throws Exception
     */
    @Test
    void getRocketById_notFound() throws Exception {
        mockMvc.perform(
                get("/rocket/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
