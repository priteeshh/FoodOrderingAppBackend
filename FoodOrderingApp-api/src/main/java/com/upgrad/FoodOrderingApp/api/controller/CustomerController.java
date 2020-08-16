package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.PasswordCryptographyProvider;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * Signup response entity.
     *
     * @param signupCustomerRequest the signup customer request
     * @return the response entity
     * @throws SignUpRestrictedException the sign up restricted exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(@RequestBody final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {
        if (signupCustomerRequest.getFirstName() == null || signupCustomerRequest.getFirstName().isEmpty() ||
                signupCustomerRequest.getEmailAddress() == null || signupCustomerRequest.getEmailAddress().isEmpty() ||
                signupCustomerRequest.getContactNumber() == null || signupCustomerRequest.getContactNumber().isEmpty() ||
                signupCustomerRequest.getPassword() == null || signupCustomerRequest.getPassword().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled.");
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstname(signupCustomerRequest.getFirstName());
        customerEntity.setLastname(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContact_number(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        final CustomerEntity createdUserEntity = customerService.saveCustomer(customerEntity);
        SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse().id(createdUserEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
    }

    /**
     * Login response entity.
     *
     * @param authorization the authorization
     * @return the response entity
     * @throws AuthenticationFailedException the authentication failed exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
        if(authorization.split("Basic ").length != 2){
            throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
        }
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedCredentials = new String(decode);
        String[] credentials = decodedCredentials.split(":");
        if(credentials.length != 2){
            throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
        }
        CustomerAuthEntity customerAuthEntity = customerService.authenticate(credentials[0], credentials[1]);

        LoginResponse loginResponse = new LoginResponse().id(customerAuthEntity.getCustomer().getUuid()).message("LOGGED IN SUCCESSFULLY")
                .firstName(customerAuthEntity.getCustomer().getFirstname())
                .lastName(customerAuthEntity.getCustomer().getLastname())
                .emailAddress(customerAuthEntity.getCustomer().getEmail())
                .contactNumber(customerAuthEntity.getCustomer().getContact_number());
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", customerAuthEntity.getAccessToken());
        return new ResponseEntity<LoginResponse>(loginResponse,headers, HttpStatus.OK);
    }

    /**
     * Logout response entity.
     *
     * @param authorization the authorization
     * @return the response entity
     * @throws AuthorizationFailedException the authorization failed exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        String authToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerService.logout(authToken);

        LogoutResponse logoutResponse = new LogoutResponse().id(customerAuthEntity.getCustomer().getUuid()).message("LOGGED OUT SUCCESSFULLY");
        return new ResponseEntity<LogoutResponse>(logoutResponse, HttpStatus.OK);
    }

    /**
     * Update customer details response entity.
     *
     * @param authorization         the authorization
     * @param updateCustomerRequest the update customer request
     * @return the response entity
     * @throws AuthorizationFailedException the authorization failed exception
     * @throws UpdateCustomerException      the update customer exception
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/customer", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomerDetails(@RequestHeader("authorization") final String authorization, @RequestBody final UpdateCustomerRequest updateCustomerRequest) throws AuthorizationFailedException, UpdateCustomerException {
        if(updateCustomerRequest.getFirstName() == null || updateCustomerRequest.getFirstName() == ""){
            throw new UpdateCustomerException("UCR-002","First name field should not be empty");
        }
        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);

        customerEntity.setFirstname(updateCustomerRequest.getFirstName());
        customerEntity.setLastname(updateCustomerRequest.getLastName());
        CustomerEntity updatedCustomerEntity = customerService.updateCustomer(customerEntity);

        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse().id(updatedCustomerEntity.getUuid()).status("CUSTOMER DETAILS UPDATED SUCCESSFULLY").firstName(updatedCustomerEntity.getFirstname()).lastName(updatedCustomerEntity.getLastname());
        return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse, HttpStatus.OK);
    }

    /**
     * Update password response entity.
     *
     * @param authorization         the authorization
     * @param updatePasswordRequest the update password request
     * @return the response entity
     * @throws AuthorizationFailedException  the authorization failed exception
     * @throws AuthenticationFailedException the authentication failed exception
     * @throws UpdateCustomerException       the update customer exception
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/customer/password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestHeader("authorization") final String authorization, @RequestBody final UpdatePasswordRequest updatePasswordRequest) throws AuthorizationFailedException, AuthenticationFailedException, UpdateCustomerException {
        if(updatePasswordRequest.getOldPassword() == null || updatePasswordRequest.getOldPassword() == "" ||
                updatePasswordRequest.getNewPassword() == null || updatePasswordRequest.getNewPassword() == ""){
            throw new UpdateCustomerException("UCR-003","No field should be empty");
        }
        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);
        CustomerEntity updatedCustomerEntity = customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword(),updatePasswordRequest.getNewPassword(),customerEntity);

        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse().id(updatedCustomerEntity.getUuid()).status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");

        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse,HttpStatus.OK);
    }

}
