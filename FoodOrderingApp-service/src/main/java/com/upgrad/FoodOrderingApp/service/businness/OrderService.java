package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private CouponDao couponDao;

    public CouponEntity getAllCouponsByName(String couponName) throws CouponNotFoundException {
        CouponEntity categoryEntity = couponDao.getCouponByName(couponName);
        if (categoryEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        if (couponName.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        return categoryEntity;
    }

    public CouponEntity getCouponByUUID(String couponUUID) throws CouponNotFoundException {
        CouponEntity couponEntity = couponDao.getCouponByUUID(couponUUID);
        if (couponEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        if (couponUUID.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        return couponEntity;
    }

    public List<OrdersEntity> getOrdersByCustomer(CustomerEntity customerEntity) {
        List<OrdersEntity> allOrders = orderDao.getOrdersByCustomer(customerEntity);
        return allOrders;

    }

    public List<OrderItemEntity> getOrderItems(OrdersEntity ordersEntity) {
        List<OrderItemEntity> orderItemEntities = orderItemDao.getOrderItems(ordersEntity);
        return orderItemEntities;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrdersEntity saveOrder(OrdersEntity orderEntity) {
        return orderDao.createOrder(orderEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return orderItemDao.createOrderItemEntity(orderItemEntity);
    }

}