package com.enigma.nutrismartbe.entity;

import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "mst_account")
public class Account {

    @Id
    @GeneratedValue(generator = "id_account", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name= "id_account", strategy = "uuid")
    private String id;
    @Column(unique = true, length = 15, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 15, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ActiveStatusEnum isActive;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<ConversationDetail> conversationDetails;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties(value = {"transactions","account"})
    private List<Transaction> transactions;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnoreProperties(value = {"account"})
    private Profile profile;
    public Account(){

    }

    public ActiveStatusEnum getIsActive() {
        return isActive;
    }

    public void setIsActive(ActiveStatusEnum isActive) {
        this.isActive = isActive;
    }

    public List<ConversationDetail> getConversationDetails() {
        return conversationDetails;
    }

    public void setConversationDetails(List<ConversationDetail> conversationDetails) {
        this.conversationDetails = conversationDetails;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}