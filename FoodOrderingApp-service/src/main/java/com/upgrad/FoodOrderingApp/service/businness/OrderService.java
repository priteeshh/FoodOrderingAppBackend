package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Order service.
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CustomerDao customerDao;

    /**
     * Gets coupon by coupon name.
     *
     * @param couponName the coupon name
     * @return the coupon by coupon name
     * @throws CouponNotFoundException the coupon not found exception
     */
    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {
        CouponEntity categoryEntity = couponDao.getCouponByName(couponName);
        if (categoryEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        if (couponName.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        return categoryEntity;
    }

    /**
     * Gets coupon by coupon id.
     *
     * @param couponUUID the coupon uuid
     * @return the coupon by coupon id
     * @throws CouponNotFoundException the coupon not found exception
     */
    public CouponEntity getCouponByCouponId(String couponUUID) throws CouponNotFoundException {
        CouponEntity couponEntity = couponDao.getCouponByUUID(couponUUID);
        if (couponEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        if (couponUUID.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        return couponEntity;
    }

    /**
     * Gets order items.
     *
     * @param orderEntity the order entity
     * @return the order items
     */
    public List<OrderItemEntity> getOrderItems(OrderEntity orderEntity) {
        List<OrderItemEntity> orderItemEntities = orderItemDao.getOrderItems(orderEntity);
        return orderItemEntities;
    }

    /**
     * Save order order entity.
     *
     * @param orderEntity the order entity
     * @return the order entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderDao.createOrder(orderEntity);
    }

    /**
     * Save order item order item entity.
     *
     * @param orderItemEntity the order item entity
     * @return the order item entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return orderItemDao.createOrderItemEntity(orderItemEntity);
    }

    /**
     * Gets orders by customers.
     *
     * @param customerUUID the customer uuid
     * @return the orders by customers
     */
    public List<OrderEntity> getOrdersByCustomers(String customerUUID) {
        return orderDao.getOrdersByCustomers(customerDao.getCustomerByUUID(customerUUID));
    }

}