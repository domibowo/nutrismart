package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Profile;
import com.enigma.nutrismartbe.enums.StatusUserEnum;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.ProfileRepository;
import com.enigma.nutrismartbe.service.ProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceImplTest {

    @Autowired
    ProfileRepository repository;

    @Autowired
    ProfileService service;

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

//    @AfterEach
//    void cleanUpAgain(){
//        repository.deleteAll();
//    }

    @Test
    void saveProfile_shouldCreateOneProfile_whenInvoked() {
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        service.saveProfile(profile);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getProfile_shouldGetSpecificProfileID_whenInvoked() {
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        repository.save(profile);
        Profile profile1 = service.getProfile(profile.getId());
        assertEquals(profile.getId(), profile1.getId());
    }

    @Test
    void getProfile_shouldThrowException_whenIDNotFound(){
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        repository.save(profile);
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProfile("cow");
        });
    }

    @Test
    void getAllProfiles_shouldReturnTwoProfiles_whenInvoked() {
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        repository.save(profile);

        Profile profile1 = new Profile();
        profile1.setStatus(StatusUserEnum.ADMIN);
        repository.save(profile1);

        Page<Profile> profiles = service.getAllProfiles(PageRequest.of(0,2));
        assertEquals(2, profiles.getTotalElements());
    }

    @Test
    void removeProfile_shouldRemoveSpecificID_whenInvoked() {
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        repository.save(profile);

        service.removeProfile(profile.getId());
        assertFalse(repository.findById(profile.getId()).isPresent());
    }
}