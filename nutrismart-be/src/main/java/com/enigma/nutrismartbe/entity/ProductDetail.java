package com.enigma.nutrismartbe.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "product_detail")
public class ProductDetail {
    @Id
    @GeneratedValue(generator = "id_product_detail", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_product_detail", strategy = "uuid")
    private String id;
    private String description;
    private String nutritionFact;
    private String calorie;

    @OneToOne(mappedBy = "productDetail")
    @JsonIgnoreProperties(value = {"category"})
    private Product product;

    public ProductDetail() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getNutritionFact() {
        return nutritionFact;
    }
    public void setNutritionFact(String nutritionFact) {
        this.nutritionFact = nutritionFact;
    }
    public String getCalorie() {
        return calorie;
    }
    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
