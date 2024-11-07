package tn.esprit.spring.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.services.ISkierServices;

import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
public class SkierRestControllerTest {

    @Mock
    private ISkierServices skierServices;

    @InjectMocks
    private SkierRestController skierRestController;

    private MockMvc mockMvc;

    @Test
    public void testAddSkier() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(skierRestController).build();

        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");

        when(skierServices.addSkier(any(Skier.class))).thenReturn(skier);

        mockMvc.perform(post("/skier/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numSkier", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));

        verify(skierServices, times(1)).addSkier(any(Skier.class));
    }



    @Test
    public void testGetById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(skierRestController).build();

        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");

        when(skierServices.retrieveSkier(1L)).thenReturn(skier);

        mockMvc.perform(get("/skier/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numSkier", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));

        verify(skierServices, times(1)).retrieveSkier(1L);
    }

    @Test
    public void testGetAllSkiers() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(skierRestController).build();

        Skier skier1 = new Skier();
        skier1.setNumSkier(1L);
        skier1.setFirstName("John");
        skier1.setLastName("Doe");

        Skier skier2 = new Skier();
        skier2.setNumSkier(2L);
        skier2.setFirstName("Jane");
        skier2.setLastName("Smith");

        List<Skier> skiers = Arrays.asList(skier1, skier2);

        when(skierServices.retrieveAllSkiers()).thenReturn(skiers);

        mockMvc.perform(get("/skier/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));

        verify(skierServices, times(1)).retrieveAllSkiers();
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(skierRestController).build();

        doNothing().when(skierServices).removeSkier(1L);

        mockMvc.perform(delete("/skier/delete/1"))
                .andExpect(status().isOk());

        verify(skierServices, times(1)).removeSkier(1L);
    }
}
