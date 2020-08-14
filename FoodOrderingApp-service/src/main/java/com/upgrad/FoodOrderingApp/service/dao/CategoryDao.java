package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CategoryEntity> getCategoryOrderedByName() {
        try {
            return entityManager.createNamedQuery("categoryOrderByName", CategoryEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<CategoryItemEntity> getAllItemsForCategory(String categoryUUID) {
        try {
            return entityManager.createNamedQuery("itemBycategoryUUID", CategoryItemEntity.class)
                    .setParameter("categoryUUID", categoryUUID).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }


}
