package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllRestaurants", query = "select p from RestaurantEntity p order by customerRating desc"),
        @NamedQuery(name = "getRestaurantByName", query = "select r from RestaurantEntity r where lower(r.restaurantName) like:restaurantName"),
        @NamedQuery(name = "getRestaurantById", query = "select p from RestaurantEntity p where p.uuid =:uuid")
})
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "restaurant_name")
    @Size(max = 50)
    private String restaurantName;

    @Column(name = "photo_url")
    @Size(max = 255)
    private String photoUrl;

    @Column(name = "customer_rating")
    private Double customerRating;

    @Column(name = "average_price_for_two")
    private Integer avgPriceForTwo;

    @Column(name = "number_of_customers_rated")
    private Integer numberOfCustomerRating;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getAvgPriceForTwo() {
        return avgPriceForTwo;
    }

    public void setAvgPriceForTwo(Integer avgPriceForTwo) {
        this.avgPriceForTwo = avgPriceForTwo;
    }

    public Integer getNumberOfCustomerRating() {
        return numberOfCustomerRating;
    }

    public void setNumberOfCustomerRating(Integer numberOfCustomerRating) {
        this.numberOfCustomerRating = numberOfCustomerRating;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
