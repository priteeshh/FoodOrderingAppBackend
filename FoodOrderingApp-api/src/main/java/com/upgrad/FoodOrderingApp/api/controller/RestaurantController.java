package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Restaurant controller.
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)

public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    /**
     * Get all restaurants
     *
     * @return the response entity
     */
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

    /**
     * Gets restaurant by name.
     *
     * @param restaurantName the restaurant name
     * @return the restaurant by name
     * @throws RestaurantNotFoundException the restaurant not found exception
     */
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

    /**
     * Gets restaurant by category id.
     *
     * @param categoryId the category id
     * @return the restaurant by category id
     * @throws RestaurantNotFoundException the restaurant not found exception
     * @throws CategoryNotFoundException   the category not found exception
     */
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

    /**
     * Gets restaurant by id.
     *
     * @param restaurantId the restaurant id
     * @return the restaurant by id
     * @throws RestaurantNotFoundException the restaurant not found exception
     */
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantById(@PathVariable("restaurant_id") String restaurantId) throws RestaurantNotFoundException {

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);
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
                listItemList.add(new ItemList().id(UUID.fromString(item.getUuid())).itemName(item.getItemName()).itemType(typeEnum).price(item.getPrice()));
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

    /**
     * Update restaurant details response entity.
     *
     * @param authorization  the authorization
     * @param resttId        the restt id
     * @param customerRating the customer rating
     * @return the response entity
     * @throws AuthorizationFailedException the authorization failed exception
     * @throws RestaurantNotFoundException  the restaurant not found exception
     * @throws InvalidRatingException       the invalid rating exception
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/restaurant/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantDetails(@RequestHeader("authorization") final String authorization, @PathVariable("restaurant_id") final String resttId,
            @RequestParam(name = "customer_rating", required = true) Double customerRating) throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException {
        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);
        if (resttId == null || resttId.isEmpty()) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(resttId);
        RestaurantEntity updateRestaurantEntity = restaurantService.updateRestaurantRating(restaurantEntity, customerRating);
        RestaurantUpdatedResponse restaurantUpdatedResponse = new RestaurantUpdatedResponse().id(UUID.fromString(resttId)).status("RESTAURANT RATING UPDATED SUCCESSFULLY");
        return new ResponseEntity<RestaurantUpdatedResponse>(restaurantUpdatedResponse, HttpStatus.OK);
    }

}
