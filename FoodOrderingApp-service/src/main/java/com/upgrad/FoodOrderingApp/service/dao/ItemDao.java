package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;
    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        try {
            return entityManager.createNamedQuery("getItemsByCategoryRestaurant", ItemEntity.class).setParameter("categoryId", categoryId).setParameter("restaurantId", restaurantId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public ItemEntity getItemByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("itemByUUID", ItemEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<ItemEntity> getItemsByPopularity(Integer restaurantId) {
        try {
            List<ItemEntity> listItemEntity = entityManager.createNamedQuery("restaurantItems", ItemEntity.class).setParameter("restaurantId", restaurantId).setMaxResults(5).getResultList();
            List<ItemEntity> topFiveListItemEntity = new ArrayList<>();
            int listSize = listItemEntity.size();
            if (listSize > 0) {
                topFiveListItemEntity.addAll(listItemEntity.subList(0, Math.min(listSize, 5)));
            }
            return topFiveListItemEntity;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
