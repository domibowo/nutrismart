package com.enigma.nutrismartbe.entity;

import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_voucher")
public class Voucher {

    @Id
    @GeneratedValue(generator = "id_voucher", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_voucher", strategy = "uuid")
    private String id;
    private String name;
    private Double value;
    private Timestamp valid;

    @OneToMany(mappedBy = "voucher")
    @JsonIgnoreProperties(value = {"transactions"})
    private List<Transaction> transactions = new ArrayList<>();

    public Voucher() {
    }

    public Timestamp getValid() {
        return valid;
    }

    public void setValid(Timestamp valid) {
        this.valid = valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}