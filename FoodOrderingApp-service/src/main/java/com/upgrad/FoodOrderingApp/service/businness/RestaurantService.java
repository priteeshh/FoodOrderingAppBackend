package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    public List<RestaurantEntity> restaurantsByRating(){
        List<RestaurantEntity> restaurantEntityList = restaurantDao.getAllRestaurants();
        return restaurantEntityList;
    }

    public List<RestaurantEntity> getRestaurantByName(String searchName) throws RestaurantNotFoundException {
        if(searchName == null || searchName == ""){
            throw new RestaurantNotFoundException("RNF-003","Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantEntityList = restaurantDao.getRestaurantByName(searchName.toLowerCase());
        return restaurantEntityList;
    }

}
