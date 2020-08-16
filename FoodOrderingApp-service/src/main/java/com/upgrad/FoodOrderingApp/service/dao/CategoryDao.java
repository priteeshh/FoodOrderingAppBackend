package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Category dao.
 */
@Repository
public class CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets category ordered by name.
     *
     * @return the category ordered by name
     */
    public List<CategoryEntity> getCategoryOrderedByName() {
        try {
            return entityManager.createNamedQuery("categoryOrderByName", CategoryEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets category by id.
     *
     * @param categoryUUID the category uuid
     * @return the category by id
     */
    public CategoryEntity getCategoryById(String categoryUUID) {
        try {
            return entityManager.createNamedQuery("categoryByUUID", CategoryEntity.class)
                    .setParameter("categoryUUID", categoryUUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Get categories by restaurant list.
     *
     * @param restaurantUUID the restaurant uuid
     * @return the list
     */
    public List<RestaurantCategoryEntity> getCategoriesByRestaurant(String restaurantUUID){
        try {
            return entityManager.createNamedQuery("getCategoriesByRestaurant", RestaurantCategoryEntity.class).setParameter("restaurantUUID", restaurantUUID).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
