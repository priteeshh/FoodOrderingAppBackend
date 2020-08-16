package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Customer dao.
 */
@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Create customer customer entity.
     *
     * @param customerEntity the customer entity
     * @return the customer entity
     */
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
            entityManager.persist(customerEntity);
            return customerEntity;
    }

    /**
     * Gets user by contact number.
     *
     * @param contact_number the contact number
     * @return the user by contact number
     */
    public CustomerEntity getUserByContactNumber(String contact_number) {
        try {
            return entityManager.createNamedQuery("getCustomerByContactNumber", CustomerEntity.class).setParameter("contact_number", contact_number).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Generate auth token customer auth entity.
     *
     * @param customerAuthEntity the customer auth entity
     * @return the customer auth entity
     */
    public CustomerAuthEntity generateAuthToken(CustomerAuthEntity customerAuthEntity){
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }

    /**
     * Get customer from auth token customer auth entity.
     *
     * @param accessToken the access token
     * @return the customer auth entity
     */
    public CustomerAuthEntity getCustomerFromAuthToken(String accessToken){
        try {
            return entityManager.createNamedQuery("getCustomerFromAuthToKen", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Customer logout customer auth entity.
     *
     * @param customerAuthEntity the customer auth entity
     * @return the customer auth entity
     */
    public CustomerAuthEntity customerLogout(CustomerAuthEntity customerAuthEntity){
        try {
            entityManager.merge(customerAuthEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerAuthEntity;
    }

    /**
     * Update customer customer entity.
     *
     * @param customerEntity the customer entity
     * @return the customer entity
     */
    public CustomerEntity updateCustomer(CustomerEntity customerEntity){
        try {
            entityManager.merge(customerEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerEntity;
    }

    /**
     * Update password customer entity.
     *
     * @param customerEntity the customer entity
     * @return the customer entity
     */
    public CustomerEntity updatePassword(CustomerEntity customerEntity){
        try {
            entityManager.merge(customerEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerEntity;
    }

    /**
     * Gets customer by uuid.
     *
     * @param uuid the uuid
     * @return the customer by uuid
     */
    public CustomerEntity getCustomerByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("customerByUUID", CustomerEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
