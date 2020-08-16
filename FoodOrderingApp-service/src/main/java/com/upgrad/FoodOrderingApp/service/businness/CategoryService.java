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

/**
 * The type Category service.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * Gets all categories ordered by name.
     *
     * @return the all categories ordered by name
     */
    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        List<CategoryEntity> categoryEntity = categoryDao.getCategoryOrderedByName();
        return categoryEntity;
    }

    /**
     * Gets category by id.
     *
     * @param categoryUuid the category uuid
     * @return the category by id
     * @throws CategoryNotFoundException the category not found exception
     */
    public CategoryEntity getCategoryById(String categoryUuid) throws CategoryNotFoundException {
        if (categoryUuid.equals("")) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryUuid);

        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        return categoryEntity;
    }


    /**
     * Gets categories by restaurant.
     *
     * @param restaurantId the restaurant id
     * @return the categories by restaurant
     */
    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) {
        List<RestaurantCategoryEntity> listRestaurantCategoryEntity = categoryDao.getCategoriesByRestaurant(restaurantId);
        List<CategoryEntity> listCategoryEntity = new ArrayList<>();
        for (RestaurantCategoryEntity restaurantCategoryEntity : listRestaurantCategoryEntity) {
            listCategoryEntity.add(restaurantCategoryEntity.getCategory());
        }
        return listCategoryEntity;
    }


}
