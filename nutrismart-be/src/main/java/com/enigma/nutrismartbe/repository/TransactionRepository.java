package com.enigma.nutrismartbe.repository;

import com.enigma.nutrismartbe.entity.Transaction;
import com.enigma.nutrismartbe.enums.StatusEnum;
import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    public Transaction findByStatusEnumAndAccount_Id(TransactionStatusEnum status, String id);
    public Page<Transaction> findAllByStatusEnum(TransactionStatusEnum status, Pageable pageable);
    public Page<Transaction> findAllTransactionsByAccount_Id(String id, Pageable pageable);
    public Page<Transaction> findAllByStatusEnumAndAccount_Id(TransactionStatusEnum status, String id, Pageable pageable);
}
