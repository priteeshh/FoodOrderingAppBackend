package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Customer service.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    /**
     * Save customer customer entity.
     *
     * @param customerEntity the customer entity
     * @return the customer entity
     * @throws SignUpRestrictedException the sign up restricted exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {

        if(!isValidEmailId(customerEntity.getEmail())){
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }
        if (!customerEntity.getContact_number().matches("^[0-9]{10}$")) {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }
        if (!customerEntity.getPassword().matches("^(?=.*[a-z]){3,}(?=.*[A-Z]){2,}(?=.*[0-9]){2,}(?=.*[!@#$%^&*()--__+.]){1,}.{8,}$")) {
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }
        CustomerEntity existedCustomer = customerDao.getUserByContactNumber(customerEntity.getContact_number());
        if (existedCustomer != null) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
        }
        String[] encryptedPassword = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedPassword[0]);
        customerEntity.setPassword(encryptedPassword[1]);
        return customerDao.createCustomer(customerEntity);
    }

    /**
     * Is valid email id boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isValidEmailId(String email) {
        String emailPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Authenticate customer auth entity.
     *
     * @param contactNumber the contact number
     * @param password      the password
     * @return the customer auth entity
     * @throws AuthenticationFailedException the authentication failed exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(final String contactNumber, final String password) throws AuthenticationFailedException {
            CustomerEntity customerEntity = customerDao.getUserByContactNumber(contactNumber);
            if(customerEntity == null){
                throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
            }
            String encryptedPassword = passwordCryptographyProvider.encrypt(password,customerEntity.getSalt());
            if(encryptedPassword.equals(customerEntity.getPassword())){
                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
                CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
                customerAuthEntity.setUuid(UUID.randomUUID().toString());
                customerAuthEntity.setCustomer(customerEntity);
                final ZonedDateTime now = ZonedDateTime.now();
                final ZonedDateTime expires = now.plusHours(8);
                customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(),now,expires));
                customerAuthEntity.setLogin_at(now);
                customerAuthEntity.setExpires_at(expires);
                customerDao.generateAuthToken(customerAuthEntity);
                return customerAuthEntity;
            }else{
                throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
            }
    }

    /**
     * Logout customer auth entity.
     *
     * @param authToken the auth token
     * @return the customer auth entity
     * @throws AuthorizationFailedException the authorization failed exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(final String authToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = getAuthCustomer(authToken);
        customerAuthEntity.setLogout_at(ZonedDateTime.now());
        CustomerAuthEntity logoutCustomerAuthEntity = customerDao.customerLogout(customerAuthEntity);
        return logoutCustomerAuthEntity;
    }

    /**
     * Gets auth customer.
     *
     * @param authToken the auth token
     * @return the auth customer
     * @throws AuthorizationFailedException the authorization failed exception
     */
    public CustomerAuthEntity getAuthCustomer(final String authToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerFromAuthToken(authToken);
        if(customerAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001","Customer is not Logged in.");
        }
        if(customerAuthEntity.getLogout_at() != null){
            throw new AuthorizationFailedException("ATHR-002","Customer is logged out. Log in again to access this endpoint.");
        }
        if(customerAuthEntity.getExpires_at().compareTo(ZonedDateTime.now()) <= 0){
            throw new AuthorizationFailedException("ATHR-003","Your session is expired. Log in again to access this endpoint.");
        }
        return customerAuthEntity;
    }

    /**
     * Gets customer.
     *
     * @param authToken the auth token
     * @return the customer
     * @throws AuthorizationFailedException the authorization failed exception
     */
    public CustomerEntity getCustomer(final String authToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerFromAuthToken(authToken);
        if(customerAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001","Customer is not Logged in.");
        }
        if(customerAuthEntity.getLogout_at() != null){
            throw new AuthorizationFailedException("ATHR-002","Customer is logged out. Log in again to access this endpoint.");
        }
        if(customerAuthEntity.getExpires_at().compareTo(ZonedDateTime.now()) <= 0){
            throw new AuthorizationFailedException("ATHR-003","Your session is expired. Log in again to access this endpoint.");
        }
        return customerAuthEntity.getCustomer();
    }

    /**
     * Update customer customer entity.
     *
     * @param customerEntity the customer entity
     * @return the customer entity
     * @throws AuthorizationFailedException the authorization failed exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomer(CustomerEntity customerEntity) throws AuthorizationFailedException {
        CustomerEntity updatedCustomerEntity =  customerDao.updateCustomer(customerEntity);
        return  updatedCustomerEntity;
    }

    /**
     * Update customer password customer entity.
     *
     * @param oldPassword    the old password
     * @param newPassword    the new password
     * @param customerEntity the customer entity
     * @return the customer entity
     * @throws UpdateCustomerException the update customer exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomerPassword(String oldPassword, String newPassword,CustomerEntity customerEntity) throws  UpdateCustomerException {
        if (!newPassword.matches("^(?=.*[a-z]){3,}(?=.*[A-Z]){2,}(?=.*[0-9]){2,}(?=.*[#@$%&*!^]){1,}.{8,}$")) {
            throw new UpdateCustomerException("UCR-001", "Weak password!");
        }
        String encryptedOldPassword = passwordCryptographyProvider.encrypt(oldPassword,customerEntity.getSalt());
        if(encryptedOldPassword.equals(customerEntity.getPassword())){
            String encryptedNewPassword = passwordCryptographyProvider.encrypt(newPassword,customerEntity.getSalt());
            customerEntity.setPassword(encryptedNewPassword);
            customerDao.updatePassword(customerEntity);
            return customerEntity;
        }else{
            throw new UpdateCustomerException("UCR-004", "Incorrect old password!");
        }
    }

}
