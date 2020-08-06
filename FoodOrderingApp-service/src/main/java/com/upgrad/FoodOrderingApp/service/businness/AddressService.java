package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, CustomerEntity customerEntity) throws SaveAddressException {
        if(addressEntity.getFlatBuildingName() == null || addressEntity.getFlatBuildingName() == "" ||
                addressEntity.getLocality() == null || addressEntity.getLocality() == "" ||
                addressEntity.getCity() == null || addressEntity.getCity() == ""  ||
                addressEntity.getPincode() == null || addressEntity.getPincode() == ""){
            throw new SaveAddressException("SAR-001","No field can be empty");
        }

        if (!addressEntity.getPincode().matches("^[0-9]{6}$")) {
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }
        AddressEntity savedAddressEntity = addressDao.saveAddress(addressEntity);
        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setAddress(savedAddressEntity);
        customerAddressEntity.setCustomer(customerEntity);
        CustomerAddressEntity savedCustomerAddressEntity = addressDao.saveCustomerAddress(customerAddressEntity);
        return savedAddressEntity;
    }

    public StateEntity getStateByUUID(String stateUUID) throws AddressNotFoundException {
       StateEntity state = addressDao.getState(stateUUID);
       if(state == null){
           throw new AddressNotFoundException("ANF-002","No state by this id");
       }
        return state;
    }

    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {
        List<CustomerAddressEntity> customerAddressEntityList = addressDao.getAllCustomerAddress(customerEntity);
        List<AddressEntity> addressList = new ArrayList<>();
        for (CustomerAddressEntity address : customerAddressEntityList) {
            addressList.add(address.getAddress());
        }
        return addressList;
    }
    public AddressEntity getAddressByUUID(String addressId, CustomerEntity customerEntity) throws AuthorizationFailedException, AddressNotFoundException {
        CustomerAddressEntity customerAddressEntity = addressDao.getAddressFromUUID(addressId);
        if(customerAddressEntity == null){
            throw new AddressNotFoundException("ANF-003","No address by this id");
        }
        if(customerEntity.getUuid() != (customerAddressEntity.getCustomer().getUuid())){
            throw new AuthorizationFailedException("ATHR-004","You are not authorized to view/update/delete any one else's address");
        }
        return customerAddressEntity.getAddress();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteAddress(AddressEntity addressEntity) throws AuthorizationFailedException, AddressNotFoundException {
        AddressEntity deletedAddressEntity = addressDao.deleteAddress(addressEntity);
        return deletedAddressEntity;
    }

    public List<StateEntity> getAllStates(){
        List<StateEntity> stateEntityList = addressDao.getAllState();
        return stateEntityList;
    }

}
