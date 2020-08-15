package com.upgrad.FoodOrderingApp.service.entity;// default package

import javax.persistence.*;

@Entity
@Table(name = "coupon", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
@NamedQueries({
        @NamedQuery(name = "getCouponByName", query = "select c from CouponEntity c where c.couponName =:couponName"),
        @NamedQuery(name = "getCouponByUUID", query = "select c from CouponEntity c where c.uuid =:couponUUID")
})

public class CouponEntity implements java.io.Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "percent", nullable = false)
    private int percent;

    public CouponEntity() {
    }

    public CouponEntity(String uuid, String couponName, int percent) {
        this.uuid = uuid;
        this.couponName = couponName;
        this.percent = percent;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getPercent() {
        return this.percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

}
