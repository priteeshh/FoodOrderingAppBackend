package com.upgrad.FoodOrderingApp.service.entity;// default package

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
@NamedQueries(
		{
				@NamedQuery(name = "getAllOrders", query = "select o from OrdersEntity o where o.customer =:customer order by o.date desc")
		}
)
public class OrdersEntity implements java.io.Serializable {

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

	public OrdersEntity() {
	}

	public OrdersEntity(int id, AddressEntity address, CustomerEntity customer, RestaurantEntity restaurant, String uuid, BigDecimal bill,
                        Date date) {
		this.id = id;
		this.address = address;
		this.customer = customer;
		this.restaurant = restaurant;
		this.uuid = uuid;
		this.bill = bill;
		this.date = date;
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
