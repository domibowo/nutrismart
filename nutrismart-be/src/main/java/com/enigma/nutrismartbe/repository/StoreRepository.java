package com.enigma.nutrismartbe.repository;

import com.enigma.nutrismartbe.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}
