package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Order item dao.
 */
@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Create order item entity order item entity.
     *
     * @param orderItemEntity the order item entity
     * @return the order item entity
     */
    public OrderItemEntity createOrderItemEntity(OrderItemEntity orderItemEntity) {
        entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }

    /**
     * Gets order items.
     *
     * @param orderEntity the order entity
     * @return the order items
     */
    public List<OrderItemEntity> getOrderItems(OrderEntity orderEntity) {

        try {
            return entityManager.createNamedQuery("getOrderItems", OrderItemEntity.class).setParameter("order", orderEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
