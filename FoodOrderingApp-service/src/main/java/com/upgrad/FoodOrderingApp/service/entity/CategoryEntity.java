package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "categoryOrderByName", query = "select c from CategoryEntity c order by c.categoryName")
})
public class CategoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "category_name")
    @Size(max = 255)
    private String categoryName;

    public CategoryEntity() {
    }

    public CategoryEntity(@Size(max =255) String uuid, @Size(max =255) String categoryName){
        this.uuid = uuid;
        this.categoryName = categoryName;

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
