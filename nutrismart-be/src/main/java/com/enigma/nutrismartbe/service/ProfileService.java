package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfileService {

    public Profile saveProfile(Profile profile);
    public Profile getProfile(String id);
    public Page<Profile> getAllProfiles(Pageable pageable);
    public void removeProfile(String id);
}
