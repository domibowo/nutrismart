package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Account;
import com.enigma.nutrismartbe.entity.Profile;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.exception.InactiveAccountException;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.ProfileRepository;
import com.enigma.nutrismartbe.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository repository;

    @Override
    public Profile saveProfile(Profile profile) {
        return repository.save(profile);
    }

    @Override
    public Profile getProfile(String id) {
        Profile profile;

        if (repository.findById(id).isPresent()){
            profile = repository.findById(id).get();
        } else throw new ResourceNotFoundException(id, Profile.class);
        return profile;
    }

    @Override
    public Page<Profile> getAllProfiles(Pageable pageable) {

        Page<Profile> profile= repository.findAll(pageable);
        return profile;
    }

    @Override
    public void removeProfile(String id) {
        repository.deleteById(id);
    }
}
