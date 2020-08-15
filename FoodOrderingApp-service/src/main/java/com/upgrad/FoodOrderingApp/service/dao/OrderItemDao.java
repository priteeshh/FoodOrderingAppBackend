package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;
    public OrderItemEntity createOrderItemEntity(OrderItemEntity orderItemEntity) {
        entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }

    public List<OrderItemEntity> getOrderItems(OrdersEntity ordersEntity) {

        try {
            return entityManager.createNamedQuery("getOrderItems", OrderItemEntity.class).setParameter("order", ordersEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
