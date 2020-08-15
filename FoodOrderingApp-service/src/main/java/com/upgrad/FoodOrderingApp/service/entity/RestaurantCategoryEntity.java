package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_category")
@NamedQueries({
        @NamedQuery(name = "getCategoriesByRestaurant", query = "select rc from RestaurantCategoryEntity rc where rc.restaurant.uuid =:restaurantUUID"),
        @NamedQuery(name = "getRestaurantByCategory", query = "select rc from RestaurantCategoryEntity rc where rc.category.uuid =:categoryId")
})
public class RestaurantCategoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    public RestaurantCategoryEntity() {
    }

    public RestaurantCategoryEntity(long id, CategoryEntity category, RestaurantEntity restaurant) {
        this.id = id;
        this.category = category;
        this.restaurant = restaurant;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return this.category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public RestaurantEntity getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

}
