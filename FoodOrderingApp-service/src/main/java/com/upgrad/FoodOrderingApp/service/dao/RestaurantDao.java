package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RestaurantEntity> getAllRestaurants(){
        try {
            return entityManager.createNamedQuery("getAllRestaurants", RestaurantEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public List<RestaurantEntity> getRestaurantByName(String searchName){
        try {
            return entityManager.createNamedQuery("getRestaurantByName", RestaurantEntity.class).setParameter("restaurantName", "%" + searchName + "%").getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<RestaurantCategoryEntity> getRestaurantByCategory(String categoryId) {
        try {
            return entityManager.createNamedQuery("getRestaurantByCategory", RestaurantCategoryEntity.class).setParameter("categoryId", categoryId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public RestaurantEntity getRestaurantById(String restaurantId){
        try {
            return entityManager.createNamedQuery("getRestaurantById", RestaurantEntity.class).setParameter("uuid", restaurantId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
