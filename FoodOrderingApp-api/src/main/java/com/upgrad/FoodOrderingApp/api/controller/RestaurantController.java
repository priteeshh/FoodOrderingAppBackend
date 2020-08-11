package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants(){
        List<RestaurantEntity> allRestaurants = restaurantService.restaurantsByRating();

        List<RestaurantList> restaurantList = new ArrayList<>();
        if (allRestaurants.size() > 0) {
            for (RestaurantEntity singleAddressEntity : allRestaurants) {
                List<CategoryEntity> allCategory = categoryService.getCategoriesByRestaurant(singleAddressEntity.getUuid());
                StringBuilder category = new StringBuilder();
                for (CategoryEntity c : allCategory) {
                    category.append(c.getCategoryName() + ", ");
                }
                RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress().id(UUID.fromString(singleAddressEntity.getAddress().getUuid()))
                        .flatBuildingName(singleAddressEntity.getAddress().getFlatBuildingName())
                        .locality(singleAddressEntity.getAddress().getLocality())
                        .city(singleAddressEntity.getAddress().getCity())
                        .pincode(singleAddressEntity.getAddress().getPincode())
                        .state(new RestaurantDetailsResponseAddressState()
                        .id(UUID.fromString(singleAddressEntity.getAddress().getState().getUuid()))
                        .stateName(singleAddressEntity.getAddress().getState().getStateName()));

                restaurantList.add(new RestaurantList().id(UUID.fromString(singleAddressEntity.getUuid()))
                        .restaurantName(singleAddressEntity.getRestaurantName())
                        .averagePrice(singleAddressEntity.getAvgPriceForTwo())
                        .categories(category.substring(0, category.length() - 2))
                        .address(restaurantDetailsResponseAddress)
                        .customerRating(BigDecimal.valueOf(singleAddressEntity.getCustomerRating()))
                        .numberCustomersRated(singleAddressEntity.getNumberOfCustomerRating())
                        .photoURL(singleAddressEntity.getPhotoUrl()));
            }
            }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByName(@PathVariable("restaurant_name") String addressId) throws RestaurantNotFoundException {

        List<RestaurantEntity> allRestaurants = restaurantService.getRestaurantByName(addressId);

        List<RestaurantList> restaurantList = new ArrayList<>();
        if (allRestaurants.size() > 0) {
            for (RestaurantEntity singleAddressEntity : allRestaurants) {
                List<CategoryEntity> allCategory = categoryService.getCategoriesByRestaurant(singleAddressEntity.getUuid());
                StringBuilder category = new StringBuilder();
                for (CategoryEntity c : allCategory) {
                    category.append(c.getCategoryName() + ", ");
                }
                RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress().id(UUID.fromString(singleAddressEntity.getAddress().getUuid()))
                        .flatBuildingName(singleAddressEntity.getAddress().getFlatBuildingName())
                        .locality(singleAddressEntity.getAddress().getLocality())
                        .city(singleAddressEntity.getAddress().getCity())
                        .pincode(singleAddressEntity.getAddress().getPincode())
                        .state(new RestaurantDetailsResponseAddressState()
                                .id(UUID.fromString(singleAddressEntity.getAddress().getState().getUuid()))
                                .stateName(singleAddressEntity.getAddress().getState().getStateName()));

                restaurantList.add(new RestaurantList().id(UUID.fromString(singleAddressEntity.getUuid()))
                        .restaurantName(singleAddressEntity.getRestaurantName())
                        .averagePrice(singleAddressEntity.getAvgPriceForTwo())
                        .categories(category.substring(0, category.length() - 2))
                        .address(restaurantDetailsResponseAddress)
                        .customerRating(BigDecimal.valueOf(singleAddressEntity.getCustomerRating()))
                        .numberCustomersRated(singleAddressEntity.getNumberOfCustomerRating())
                        .photoURL(singleAddressEntity.getPhotoUrl()));
            }
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);

    }
}
