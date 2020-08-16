package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The type Address entity for address table.
 */
@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "flat_buil_number")
    @NotNull
    private String flatBuildingName;

    @Column(name = "locality")
    @NotNull
    private String locality;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "pincode")
    @NotNull
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @NotNull
    private StateEntity state;


    @Column(name = "active")
    @NotNull
    private Integer active;

    @OneToOne(mappedBy = "address", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private RestaurantEntity restaurant;

    public AddressEntity(@Size(max = 200) String uuid, @Size(max = 255) String flatBuildNo, @Size(max = 255) String locality, @Size(max = 39) String city, @Size(max = 30) String pincode, StateEntity state) {
        this.id = 0;
        this.uuid = uuid;
        this.flatBuildingName = flatBuildNo;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.active = 1;
    }

    public AddressEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlatBuildingName() {
        return flatBuildingName;
    }

    public void setFlatBuildingName(String flatBuildingName) {
        this.flatBuildingName = flatBuildingName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
