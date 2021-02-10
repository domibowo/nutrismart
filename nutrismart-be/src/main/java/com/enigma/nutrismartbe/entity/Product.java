package com.enigma.nutrismartbe.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "mst_product")
public class Product {
    @Id
    @GeneratedValue(generator = "id_product", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_product", strategy = "uuid")
    private String id;

    @Column(unique = true)
    private String name;

    private Integer stock;
    private String minBundle;
    private Integer price;
    private String photo;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"product"})
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = {"product","details"})
    private List<TransactionDetail> details;

    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties(value = {"products","productDetail"})
    private List<Store> stores = new ArrayList<>();

    public Product() {
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
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public String getMinBundle() {
        return minBundle;
    }

    public void setMinBundle(String minBundle) {
        this.minBundle = minBundle;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }
}
