package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The type Address dao.
 */
@Repository
public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save address address entity.
     *
     * @param addressEntity the address entity
     * @return the address entity
     */
    public AddressEntity saveAddress(AddressEntity addressEntity){
        try {
            entityManager.persist(addressEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return addressEntity;
    }

    /**
     * Save customer address customer address entity.
     *
     * @param customerAddressEntity the customer address entity
     * @return the customer address entity
     */
    public CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity){
        try {
            entityManager.persist(customerAddressEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return customerAddressEntity;
    }

    /**
     * Get state state entity.
     *
     * @param stateUUID the state uuid
     * @return the state entity
     */
    public StateEntity getState(String stateUUID){
        try {
            return entityManager.createNamedQuery("getStateFromUUID", StateEntity.class).setParameter("UUID", stateUUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets all customer address.
     *
     * @param customer the customer
     * @return the all customer address
     */
    public List<CustomerAddressEntity> getAllCustomerAddress(CustomerEntity customer) {
        try {
            return entityManager.createNamedQuery("getCustomerAddressList", CustomerAddressEntity.class).setParameter("customer", customer).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Get address from uuid customer address entity.
     *
     * @param addressId the address id
     * @return the customer address entity
     */
    public CustomerAddressEntity getAddressFromUUID(String addressId){
        try {
            return entityManager.createNamedQuery("getCustomerAddressByUUID", CustomerAddressEntity.class).setParameter("addressId", addressId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Delete address address entity.
     *
     * @param addressEntity the address entity
     * @return the address entity
     */
    public AddressEntity deleteAddress(AddressEntity addressEntity){
        try {
            entityManager.remove(addressEntity);
        } catch (NoResultException nre) {
            return null;
        }
        return addressEntity;
    }

    /**
     * Get all state list.
     *
     * @return the list
     */
    public List<StateEntity> getAllState(){
        try {
            return entityManager.createNamedQuery("getAllState", StateEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
