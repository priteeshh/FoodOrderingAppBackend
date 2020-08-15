package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<OrdersEntity> getOrdersByCustomer(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("getAllOrders", OrdersEntity.class).setParameter("customer", customerEntity).getResultList();
            //return entityManager.createNamedQuery("getAllOrders", OrdersEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public OrdersEntity createOrder(OrdersEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }


}
