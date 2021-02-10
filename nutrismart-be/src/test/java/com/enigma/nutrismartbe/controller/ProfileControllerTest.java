package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Profile;
import com.enigma.nutrismartbe.enums.GenderEnum;
import com.enigma.nutrismartbe.enums.StatusUserEnum;
import com.enigma.nutrismartbe.repository.ProfileRepository;
import com.enigma.nutrismartbe.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProfileRepository repository;

    @Autowired
    ProfileService service;

    @Autowired
    ProfileController controller;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getProfile_shouldReturnOK_whenGettingSpecificProfileByID() throws Exception {
        Profile profile = new Profile();
        profile.setFirstName("Anonymous");
        profile.setLastName("Anonymous");
        profile.setStatus(StatusUserEnum.USER);
        profile.setPhone("0123456789");
        profile.setGender(GenderEnum.MALE);
        profile.setBirthDate(new Date());
        profile.setAddress("Fuyuki Town");
        profile.setDetail("Holy Grail");
        profile.setLatitude(0.0);
        profile.setLongitude(0.0);
        service.saveProfile(profile);

        service.getProfile(profile.getId());

        RequestBuilder builder = MockMvcRequestBuilders.get("/profile/"+profile.getId())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(profile));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveProfile_shouldReturnStatusOK_whenAbleToSaveNewProfile() throws Exception {
        Profile profile = new Profile();
        profile.setFirstName("Anonymous");
        profile.setLastName("Anonymous");
        profile.setStatus(StatusUserEnum.USER);

        RequestBuilder builder = MockMvcRequestBuilders.post("/profile/form")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(profile));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllProfiles_shouldReturnStatusOK_whenAbleToGetAllProfiles() throws Exception {
        Profile profile = new Profile();
        profile.setFirstName("Anonymous");
        profile.setLastName("Anonymous");
        profile.setStatus(StatusUserEnum.USER);
        service.saveProfile(profile);

        Profile profile1 = new Profile();
        profile1.setFirstName("Anonymous");
        profile1.setLastName("Anonymous");
        profile1.setStatus(StatusUserEnum.ADMIN);
        service.saveProfile(profile1);

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/profile")
                .param("page","1").param("size", "2")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}