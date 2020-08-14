package com.upgrad.FoodOrderingApp.service.entity;// default package

import javax.persistence.*;

@Entity
@Table(name = "order_item", schema = "public")
public class OrderItemEntity implements java.io.Serializable {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	private ItemEntity item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private OrdersEntity ordersEntity;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private int price;

	public OrderItemEntity() {
	}

	public OrderItemEntity(int id, ItemEntity item, OrdersEntity ordersEntity, int quantity, int price) {
		this.id = id;
		this.item = item;
		this.ordersEntity = ordersEntity;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemEntity getItem() {
		return this.item;
	}

	public void setItem(ItemEntity item) {
		this.item = item;
	}

	public OrdersEntity getOrdersEntity() {
		return this.ordersEntity;
	}

	public void setOrdersEntity(OrdersEntity ordersEntity) {
		this.ordersEntity = ordersEntity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
