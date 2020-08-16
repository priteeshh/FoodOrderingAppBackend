package com.upgrad.FoodOrderingApp.service.entity;// default package

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Order entity for orders table.
 */
@Entity
@Table(name = "orders")
@NamedQueries(
        {
                @NamedQuery(name = "allOrders", query = "select o from OrderEntity o where o.customer =:customer order by o.date desc"),
                @NamedQuery(name = "ordersByAddress", query = "select q from OrderEntity q where q.address = :address"),
                @NamedQuery(name = "ordersByCustomer", query = "select q from OrderEntity q where q.customer = :customer order by q.date desc ")
        }
)
public class OrderEntity implements java.io.Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @Column(name = "uuid", unique = true, nullable = false, length = 200)
    private String uuid;

    @Column(name = "bill", nullable = false)
    private BigDecimal bill;

    @Column(name = "discount")
    private BigDecimal discount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    public OrderEntity() {
    }

    public OrderEntity(@NotNull @Size(max = 200) String uuid, @NotNull Double bill, @NotNull CouponEntity coupon, @NotNull Double discount,
                       @NotNull Date date, @NotNull PaymentEntity payment,
                       @NotNull CustomerEntity customer, @NotNull AddressEntity address, RestaurantEntity restaurant) {
        this.uuid = uuid;
        this.bill = new BigDecimal(bill);
        this.coupon = coupon;
        this.discount = new BigDecimal(discount);
        this.date = date;
        this.payment = payment;
        this.customer = customer;
        this.address = address;
        this.restaurant = restaurant;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AddressEntity getAddress() {
        return this.address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public CouponEntity getCouponEntity() {
        return this.coupon;
    }

    public void setCouponEntity(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public CustomerEntity getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public PaymentEntity getPaymentEntity() {
        return this.payment;
    }

    public void setPaymentEntity(PaymentEntity payment) {
        this.payment = payment;
    }

    public RestaurantEntity getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getBill() {
        return this.bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
