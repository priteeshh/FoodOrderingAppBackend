package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
            entityManager.persist(customerEntity);
            return customerEntity;
    }
    public CustomerEntity getUserByContactNumber(String contact_number) {
        try {
            return entityManager.createNamedQuery("getCustomerByContactNumber", CustomerEntity.class).setParameter("contact_number", contact_number).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public CustomerAuthEntity generateAuthToken(CustomerAuthEntity customerAuthEntity){
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }

    public CustomerAuthEntity getCustomerFromAuthToken(String accessToken){
        try {
            return entityManager.createNamedQuery("getCustomerFromAuthToKen", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public CustomerAuthEntity customerLogout(CustomerAuthEntity customerAuthEntity){
        try {
            entityManager.merge(customerAuthEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerAuthEntity;
    }

    public CustomerEntity updateCustomer(CustomerEntity customerEntity){
        try {
            entityManager.merge(customerEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerEntity;
    }
    public CustomerEntity updatePassword(CustomerEntity customerEntity){
        try {
            entityManager.merge(customerEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerEntity;
    }
}
