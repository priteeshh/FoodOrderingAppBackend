package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Order dao.
 */
@Repository
public class OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets orders by customer.
     *
     * @param customerEntity the customer entity
     * @return the orders by customer
     */
    public List<OrderEntity> getOrdersByCustomer(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("allOrders", OrderEntity.class).setParameter("customer", customerEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Create order order entity.
     *
     * @param orderEntity the order entity
     * @return the order entity
     */
    public OrderEntity createOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }

    /**
     * Gets orders by customers.
     *
     * @param customerEntity the customer entity
     * @return the orders by customers
     */
    public List<OrderEntity> getOrdersByCustomers(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("ordersByCustomer", OrderEntity.class).setParameter("customer", customerEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
