package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Item dao.
 */
@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets items by category and restaurant.
     *
     * @param restaurantId the restaurant id
     * @param categoryId   the category id
     * @return the items by category and restaurant
     */
    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        try {
            return entityManager.createNamedQuery("getItemsByCategoryRestaurant", ItemEntity.class).setParameter("categoryId", categoryId).setParameter("restaurantId", restaurantId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets item by uuid.
     *
     * @param uuid the uuid
     * @return the item by uuid
     */
    public ItemEntity getItemByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("itemByUUID", ItemEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets items by popularity.
     *
     * @param restaurantId the restaurant id
     * @return the items by popularity
     */
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
