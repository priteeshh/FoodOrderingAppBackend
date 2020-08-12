package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
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

    @Autowired
    private ItemService itemService;

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
    public ResponseEntity<RestaurantListResponse> getRestaurantByName(@PathVariable("restaurant_name") String restaurantName) throws RestaurantNotFoundException {

        List<RestaurantEntity> allRestaurants = restaurantService.getRestaurantByName(restaurantName);

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

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByCategoryId(@PathVariable("category_id") String categoryId) throws RestaurantNotFoundException, CategoryNotFoundException {

        List<RestaurantEntity> allRestaurants = restaurantService.getRestaurantByCategory(categoryId);

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
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantById(@PathVariable("restaurant_id") String restaurantId) throws RestaurantNotFoundException {

        RestaurantEntity restaurantEntity = restaurantService.getRestaurantById(restaurantId);
        List<CategoryEntity> allCategory = categoryService.getCategoriesByRestaurant(restaurantEntity.getUuid());

        List<CategoryList> listCategoryList = new ArrayList<>();
        for (CategoryEntity c : allCategory) {
            List<ItemEntity> listItemEntity = itemService.getItemsByCategoryAndRestaurant(restaurantId, c.getUuid());
            List<ItemList> listItemList = new ArrayList<>();
            for (ItemEntity item : listItemEntity) {
                ItemList.ItemTypeEnum typeEnum = null;
                if(item.getType().equals("1")){
                    typeEnum = ItemList.ItemTypeEnum.NON_VEG;
                }else if(item.getType().equals("0")){
                    typeEnum = ItemList.ItemTypeEnum.VEG;
                }
                listItemList.add(new ItemList().id(UUID.fromString(item.getUuid())).itemName(item.getItemName())
                        .itemType(typeEnum)
                        .price(item.getPrice()));
            }
            listCategoryList.add(new CategoryList().id(UUID.fromString(c.getUuid())).categoryName(c.getCategoryName()).itemList(listItemList));
        }
        RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress().id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                .flatBuildingName(restaurantEntity.getAddress().getFlatBuildingName())
                .locality(restaurantEntity.getAddress().getLocality())
                .city(restaurantEntity.getAddress().getCity())
                .pincode(restaurantEntity.getAddress().getPincode())
                .state(new RestaurantDetailsResponseAddressState()
                        .id(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()))
                        .stateName(restaurantEntity.getAddress().getState().getStateName()));

        RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid()))
                .restaurantName(restaurantEntity.getRestaurantName())
                .averagePrice(restaurantEntity.getAvgPriceForTwo())
                .address(restaurantDetailsResponseAddress)
                .categories(listCategoryList)
                .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
                .numberCustomersRated(restaurantEntity.getNumberOfCustomerRating())
                .photoURL(restaurantEntity.getPhotoUrl());
        return new ResponseEntity<RestaurantDetailsResponse>(restaurantDetailsResponse, HttpStatus.OK);
    }
}
