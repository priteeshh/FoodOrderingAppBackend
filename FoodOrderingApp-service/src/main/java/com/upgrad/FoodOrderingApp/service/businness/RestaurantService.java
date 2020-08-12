package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
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

    public List<RestaurantEntity> getRestaurantByCategory(String categoryId) throws RestaurantNotFoundException, CategoryNotFoundException {
        if(categoryId == null || categoryId == ""){
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        }
        List<RestaurantCategoryEntity> listRestaurantCategoryEntity = restaurantDao.getRestaurantByCategory(categoryId);
        List<RestaurantEntity> listRestaurantEntity = new ArrayList<>();
        for (RestaurantCategoryEntity rc : listRestaurantCategoryEntity) {
            listRestaurantEntity.add(rc.getRestaurant());
        }
        return listRestaurantEntity;
    }

    public RestaurantEntity getRestaurantById(String restaurantId) throws RestaurantNotFoundException {
        if(restaurantId == null || restaurantId == ""){
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantById(restaurantId);
        if(restaurantEntity == null){
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }
        return restaurantEntity;
    }

}
