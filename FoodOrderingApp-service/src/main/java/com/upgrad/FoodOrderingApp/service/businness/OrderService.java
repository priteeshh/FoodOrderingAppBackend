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

    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {
        CouponEntity categoryEntity = couponDao.getCouponByName(couponName);
        if (categoryEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        if (couponName.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        return categoryEntity;
    }

    public CouponEntity getCouponByCouponId(String couponUUID) throws CouponNotFoundException {
        CouponEntity couponEntity = couponDao.getCouponByUUID(couponUUID);
        if (couponEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        if (couponUUID.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        return couponEntity;
    }

    public List<OrderItemEntity> getOrderItems(OrderEntity orderEntity) {
        List<OrderItemEntity> orderItemEntities = orderItemDao.getOrderItems(orderEntity);
        return orderItemEntities;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderDao.createOrder(orderEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return orderItemDao.createOrderItemEntity(orderItemEntity);
    }

    public List<OrderEntity> getOrdersByCustomers(String customerUUID) {
        return orderDao.getOrdersByCustomers(customerDao.getCustomerByUUID(customerUUID));
    }

}