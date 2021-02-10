package com.enigma.nutrismartbe.entity;

import com.enigma.nutrismartbe.enums.GenderEnum;
import com.enigma.nutrismartbe.enums.StatusUserEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mst_profile")
public class Profile {

    @Id
    @GeneratedValue(generator = "id_profile", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_profile", strategy = "uuid")
    private String id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String phone;

    @Enumerated(EnumType.STRING)
    private StatusUserEnum status;

    private String address;
    private String detail;
    private Double latitude;
    private Double longitude;

    @OneToOne(mappedBy = "profile")
    @JsonIgnoreProperties(value = {"profile"})
    private Account account;

    public Profile() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public StatusUserEnum getStatus() {
        return status;
    }

    public void setStatus(StatusUserEnum status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}