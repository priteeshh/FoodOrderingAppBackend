package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@NamedQueries({
        @NamedQuery(name = "getItemsByCategoryRestaurant", query = "select p from ItemEntity p, CategoryItemEntity ci, RestaurantItemEntity ri where ri.item.uuid = p.uuid and ci.item.uuid = p.uuid and ci.category.uuid =:categoryId and ri.restaurant.uuid =:restaurantId" ),
        @NamedQuery(name = "itemByUUID", query = "select ie from ItemEntity ie where ie.uuid =:uuid"),
        @NamedQuery(name = "getItemsByPopularity", query = "select c from ItemEntity c, RestaurantItemEntity ri where ri.restaurant.id = :restaurantId")
})

public class ItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "item_name")
    @Size(max = 30)
    private String itemName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "type")
    @Size(max = 10)
    private String type;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CategoryItemEntity> categoryItem = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<RestaurantItemEntity> restaurantItem = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItem = new ArrayList<>();

    public ItemEntity(){}

    public ItemEntity(@Size(max = 200) @NotNull String uuid, @Size(max = 30) @NotNull String itemName, @NotNull Integer price, @Size(max = 10) @NotNull String type) {
        this.uuid = uuid;
        this.itemName = itemName;
        this.price = price;
        this.type = type;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CategoryItemEntity> getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(List<CategoryItemEntity> categoryItem) {
        this.categoryItem = categoryItem;
    }

    public List<RestaurantItemEntity> getRestaurantItem() {
        return restaurantItem;
    }

    public void setRestaurantItem(List<RestaurantItemEntity> restaurantItem) {
        this.restaurantItem = restaurantItem;
    }
}

