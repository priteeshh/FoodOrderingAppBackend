package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Restaurant dao.
 */
@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get all restaurants list.
     *
     * @return the list
     */
    public List<RestaurantEntity> getAllRestaurants(){
        try {
            return entityManager.createNamedQuery("getAllRestaurants", RestaurantEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Get restaurant by name list.
     *
     * @param searchName the search name
     * @return the list
     */
    public List<RestaurantEntity> getRestaurantByName(String searchName){
        try {
            return entityManager.createNamedQuery("getRestaurantByName", RestaurantEntity.class).setParameter("restaurantName", "%" + searchName + "%").getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets restaurant by category.
     *
     * @param categoryId the category id
     * @return the restaurant by category
     */
    public List<RestaurantCategoryEntity> getRestaurantByCategory(String categoryId) {
        try {
            return entityManager.createNamedQuery("getRestaurantByCategory", RestaurantCategoryEntity.class).setParameter("categoryId", categoryId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Get restaurant by id restaurant entity.
     *
     * @param restaurantId the restaurant id
     * @return the restaurant entity
     */
    public RestaurantEntity getRestaurantById(String restaurantId){
        try {
            return entityManager.createNamedQuery("getRestaurantById", RestaurantEntity.class).setParameter("uuid", restaurantId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Update restaurant restaurant entity.
     *
     * @param updateRestaurantEntity the update restaurant entity
     * @return the restaurant entity
     */
    public RestaurantEntity updateRestaurant(final RestaurantEntity updateRestaurantEntity) {
        try {
            entityManager.merge(updateRestaurantEntity);
        }catch (NoResultException nre) {
            return null;
        }
        return updateRestaurantEntity;
    }
}
