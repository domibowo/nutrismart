package com.enigma.nutrismartbe.entity;
import com.enigma.nutrismartbe.enums.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "mst_category")
public class Category {

    @Id
    @GeneratedValue(generator = "id_category", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "id_category", strategy = "uuid")
    private String id;
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryName;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = {"products", "category"})
    private List<Product> products = new ArrayList<>();

    public Category() {
    }
    public Category(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public CategoryEnum getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                categoryName == category.categoryName;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName);
    }
}
