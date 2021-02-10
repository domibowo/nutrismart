package com.enigma.nutrismartbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "transaction_detail")
public class TransactionDetail {

    @Id
    @GeneratedValue(generator = "id_transaction_detail", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_transaction_detail", strategy = "uuid")
    private String id;
    private Integer subTotal;
    private Integer quantity;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "detailProduct_id")
    @JsonIgnoreProperties(value = {"product","products","category","details","productDetail","stores"})
    private Product product;

    @ManyToOne
    @JoinColumn(name = "detailTrx_id")
    @JsonIgnoreProperties(value = {"transactionDetails"})
    private Transaction transaction;

    public TransactionDetail() {
    }

    public String getId() {
        return id;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}