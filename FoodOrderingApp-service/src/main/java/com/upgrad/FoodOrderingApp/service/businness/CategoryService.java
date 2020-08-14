package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;

import java.util.List;
import java.util.ArrayList;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<CategoryEntity> getAllCategoriesOrderByName() {
        List<CategoryEntity> categoryEntity = categoryDao.getCategoryOrderedByName();
        return categoryEntity;
    }

    public List<CategoryItemEntity> getAllItemsForCategory(String uuid) throws CategoryNotFoundException {
        List<CategoryItemEntity> categoryEntity = categoryDao.getAllItemsForCategory(uuid);
        if (uuid.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        if (categoryEntity == null || categoryEntity.size() == 0) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }
        return categoryEntity;
    }

    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) {
        List<RestaurantCategoryEntity> listRestaurantCategoryEntity = categoryDao.getCategoriesByRestaurant(restaurantId);
        List<CategoryEntity> listCategoryEntity = new ArrayList<>();
        for (RestaurantCategoryEntity rc : listRestaurantCategoryEntity) {
            listCategoryEntity.add(rc.getCategory());
        }
        return listCategoryEntity;
    }
}
