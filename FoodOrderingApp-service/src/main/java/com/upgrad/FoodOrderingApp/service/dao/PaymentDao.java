package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PaymentDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PaymentEntity> getPaymentOptions() {
        try {
            return entityManager.createNamedQuery("allPaymentOptions", PaymentEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public PaymentEntity getPaymentByUUID(String paymentUUID) {
        try {
            return entityManager.createNamedQuery("getPaymentByUUID", PaymentEntity.class)
                    .setParameter("paymentUUID", paymentUUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
