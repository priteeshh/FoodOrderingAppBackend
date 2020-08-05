package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;
    public AddressEntity saveAddress(AddressEntity addressEntity){
        try {
            entityManager.persist(addressEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return addressEntity;
    }
    public CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity){
        try {
            entityManager.persist(customerAddressEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerAddressEntity;
    }

    public StateEntity getState(String stateUUID){
        try {
            return entityManager.createNamedQuery("getStateFromUUID", StateEntity.class).setParameter("UUID", stateUUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
