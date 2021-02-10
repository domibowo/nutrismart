package com.enigma.nutrismartbe.entity;

import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_transaction")
public class Transaction {

    @Id
    @GeneratedValue(generator = "id_transaction", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_transaction", strategy = "uuid")
    private String id;
    private Integer grandTotal;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    @JsonIgnoreProperties(value = {"transactions"})
    private Voucher voucher;

    @OneToMany(mappedBy = "transaction", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnoreProperties(value = {"transaction","productDetail"})
    private List<TransactionDetail> transactionDetails;

    private Timestamp trxDate;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum statusEnum;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = {"transactions","profile"})
    private Account account;

    @Transient
    private String productId;

    @Transient
    private Integer productQty;

    @Transient
    private String accountId;

    @Transient
    private String voucherName;

    @Transient
    private String detailId;

    public Transaction() {
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public Timestamp getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Timestamp trxDate) {
        this.trxDate = trxDate;
    }

    public TransactionStatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(TransactionStatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public String getProductId() {
        return productId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}