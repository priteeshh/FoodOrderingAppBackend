package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "customer_address")
public class CustomerAddressEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
