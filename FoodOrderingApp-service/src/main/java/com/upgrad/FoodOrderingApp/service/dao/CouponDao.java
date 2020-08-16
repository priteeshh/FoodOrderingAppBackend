package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * The type Coupon dao.
 */
@Repository
public class CouponDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets coupon by name.
     *
     * @param couponName the coupon name
     * @return the coupon by name
     */
    public CouponEntity getCouponByName(String couponName) {
        try {
            return entityManager.createNamedQuery("getCouponByName", CouponEntity.class)
                    .setParameter("couponName", couponName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets coupon by uuid.
     *
     * @param couponUUID the coupon uuid
     * @return the coupon by uuid
     */
    public CouponEntity getCouponByUUID(String couponUUID) {
        try {
            return entityManager.createNamedQuery("getCouponByCouponId", CouponEntity.class)
                    .setParameter("couponUUID", couponUUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
