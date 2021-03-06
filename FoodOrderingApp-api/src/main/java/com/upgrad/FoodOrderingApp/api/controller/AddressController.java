package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Address controller.
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    /**
     * Save address response entity.
     *
     * @param authorization      the authorization
     * @param saveAddressRequest the save address request
     * @return the response entity
     * @throws AuthorizationFailedException the authorization failed exception
     * @throws SaveAddressException         the save address exception
     * @throws AddressNotFoundException     the address not found exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/address", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestHeader("authorization") final String authorization, @RequestBody(required = false) final SaveAddressRequest saveAddressRequest) throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);
        StateEntity stateEntity = addressService.getStateByUUID(saveAddressRequest.getStateUuid());

        //Create new AddressEntity with given request
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setUuid(UUID.randomUUID().toString());
        addressEntity.setFlatBuildingName(saveAddressRequest.getFlatBuildingName());
        addressEntity.setLocality(saveAddressRequest.getLocality());
        addressEntity.setCity(saveAddressRequest.getCity());
        addressEntity.setPincode(saveAddressRequest.getPincode());
        addressEntity.setState(stateEntity);
        addressEntity.setActive(1);

        AddressEntity savesAddressEntity = addressService.saveAddress(addressEntity,customerEntity);

        SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(savesAddressEntity.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);

    }

    /**
     * Gets all address.
     *
     * @param authorization the authorization
     * @return the all address
     * @throws AuthorizationFailedException the authorization failed exception
     * @throws AddressNotFoundException     the address not found exception
     */
    @RequestMapping(method = RequestMethod.GET, path = "/address/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAllAddress(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, AddressNotFoundException {

        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);

        List<AddressEntity> addressListResult = addressService.getAllAddress(customerEntity);
        List<AddressList> addressList = new ArrayList<>();
        if (addressListResult.size() > 0) {
            for (AddressEntity singleAddressEntity : addressListResult) {
                AddressListState addListState = new AddressListState().id(UUID.fromString(singleAddressEntity.getState().getUuid())).stateName(singleAddressEntity.getState().getStateName());
                AddressList address = new AddressList().id(UUID.fromString(singleAddressEntity.getUuid())).flatBuildingName(singleAddressEntity.getFlatBuildingName())
                        .locality(singleAddressEntity.getLocality()).city(singleAddressEntity.getCity()).pincode(singleAddressEntity.getPincode()).state(addListState);
                addressList.add(address);
            }
        }
        AddressListResponse addListResponse = new AddressListResponse().addresses(addressList);
        return new ResponseEntity<AddressListResponse>(addListResponse, HttpStatus.OK);

    }

    /**
     * Delete address entity.
     *
     * @param authorization the authorization
     * @param addressId     the address id
     * @return the response entity
     * @throws AuthorizationFailedException the authorization failed exception
     * @throws AddressNotFoundException     the address not found exception
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/address/{address_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteAddress(@RequestHeader("authorization") final String authorization, @PathVariable("address_id") String addressId) throws AuthorizationFailedException, AddressNotFoundException {
        if(addressId == null || addressId == ""){
            throw new AddressNotFoundException("ANF-005","Address id can not be empty");
        }
        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);
        AddressEntity addressEntity = addressService.getAddressByUUID(addressId,customerEntity);
        AddressEntity deletedAddress = addressService.deleteAddress(addressEntity);

        DeleteAddressResponse deleteAddressResponse = new DeleteAddressResponse().id(UUID.fromString(deletedAddress.getUuid())).status("ADDRESS DELETED SUCCESSFULLY");

        return new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse,HttpStatus.OK);
    }

    /**
     * Get all states.
     *
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.GET, path = "/states", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatesListResponse> getAllStates(){

        List<StateEntity> allStatesList = addressService.getAllStates();
        List<StatesList> finalStates = null;
        if (allStatesList.size() != 0) {
            finalStates = new ArrayList<StatesList>();
            for (StateEntity stateEntity : allStatesList) {
                finalStates.add(new StatesList().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName()));
            }
        }
        StatesListResponse statesListResponse = new StatesListResponse().states(finalStates);
        return new ResponseEntity<StatesListResponse>(statesListResponse,HttpStatus.OK);
    }
}
