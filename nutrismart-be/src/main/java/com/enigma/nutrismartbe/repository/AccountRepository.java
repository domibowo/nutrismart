package com.enigma.nutrismartbe.repository;

import com.enigma.nutrismartbe.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
    Account findAccountsByUserNameEqualsAndPasswordEquals(String username, String pass);
    Account findByUserNameAndPassword(String username, String pass);
}
