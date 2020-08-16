package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Category entity for category table.
 */
@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "categoryOrderByName", query = "select c from CategoryEntity c order by c.categoryName"),
        @NamedQuery(name = "categoryByUUID", query = "select ci from CategoryEntity ci where ci.uuid =:categoryUUID")
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

    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<ItemEntity> items = new ArrayList<>(0);

    @ManyToMany
    @JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private List<RestaurantEntity> restaurants = new ArrayList<>(0);

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

    public List<ItemEntity> getItems() { return items; }

    public void setItems(List<ItemEntity> items) { this.items = items; }

    public List<RestaurantEntity> getRestaurants() { return restaurants; }

    public void setRestaurants(List<RestaurantEntity> restaurants) { this.restaurants = restaurants; }
}
