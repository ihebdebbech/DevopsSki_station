package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISkierServices;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkierRestController.class)
public class SkierRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISkierServices skierServices;

    @Autowired
    private ObjectMapper objectMapper;

    private Skier skier;
    private Subscription subscription;

    @BeforeEach
    public void setUp() {
        subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setSubscription(subscription);
    }

    @Test
    public void testAddSkier() throws Exception {
        when(skierServices.addSkier(Mockito.any(Skier.class))).thenReturn(skier);

        mockMvc.perform(post("/skier/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testAssignSkierToSubscription() throws Exception {
        when(skierServices.assignSkierToSubscription(1L, 2L)).thenReturn(skier);

        mockMvc.perform(put("/skier/assignToSub/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testAssignSkierToPiste() throws Exception {
        when(skierServices.assignSkierToPiste(1L, 2L)).thenReturn(skier);

        mockMvc.perform(put("/skier/assignToPiste/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testDeleteSkier() throws Exception {
        Mockito.doNothing().when(skierServices).removeSkier(1L);

        mockMvc.perform(delete("/skier/delete/1"))
                .andExpect(status().isOk());
    }
}
