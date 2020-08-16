package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Item service.
 */
@Service
public class ItemService {

    /**
     * The Item dao.
     */
    @Autowired
    ItemDao itemDao;

    /**
     * Gets items by category and restaurant.
     *
     * @param restaurantId the restaurant id
     * @param categoryId   the category id
     * @return the items by category and restaurant
     */
    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        List<ItemEntity> itemEntityList = itemDao.getItemsByCategoryAndRestaurant(restaurantId, categoryId);
        return itemEntityList;
    }

    /**
     * Gets item by uuid.
     *
     * @param uuid the uuid
     * @return the item by uuid
     * @throws ItemNotFoundException the item not found exception
     */
    public ItemEntity getItemByUUID(String uuid) throws ItemNotFoundException {
        ItemEntity itemEntity = itemDao.getItemByUUID(uuid);
        if (itemEntity == null) {
            throw new ItemNotFoundException("INF-003", "No item by this id exist");
        }
        return itemEntity;
    }

    /**
     * Gets items by popularity.
     *
     * @param restaurantEntity the restaurant entity
     * @return the items by popularity
     */
    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        return itemDao.getItemsByPopularity(restaurantEntity.getId());
    }

}
