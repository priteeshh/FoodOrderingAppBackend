package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemDao itemDao;
    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        List<ItemEntity> itemEntityList = itemDao.getItemsByCategoryAndRestaurant(restaurantId, categoryId);
        return itemEntityList;
    }
}
