package com.enigma.nutrismartbe.entity;

import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mst_store")
public class Store {

    @Id
    @GeneratedValue(generator = "id_store", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_store", strategy = "uuid")
    private String id;
    private String storeName;
    private String address;
    private String detail;
    private Double latitude;
    private Double longitude;

    @ManyToMany
    @JoinTable(name = "store_has_product", joinColumns = {@JoinColumn(name = "store_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    @JsonIgnoreProperties(value = {"stores","carts","productDetail","category"})
    private List<Product> products;

    public Store() {
    }

    public Store(String storeName) {
        this.storeName = storeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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