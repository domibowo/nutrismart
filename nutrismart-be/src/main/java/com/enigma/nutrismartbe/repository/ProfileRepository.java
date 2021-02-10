package com.enigma.nutrismartbe.repository;

import com.enigma.nutrismartbe.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    public Page<Profile> findByFirstNameAndLastName (String firstName, String lastName, Pageable pageable);
}