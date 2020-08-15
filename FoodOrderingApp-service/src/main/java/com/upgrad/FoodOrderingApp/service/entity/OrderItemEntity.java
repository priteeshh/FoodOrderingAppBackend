package com.upgrad.FoodOrderingApp.service.entity;// default package

import javax.persistence.*;

@Entity
@Table(name = "order_item", schema = "public")
@NamedQueries(
		{
				@NamedQuery(name = "getOrderItems", query = "select oi from OrderItemEntity oi where oi.order =:order")
		}
)
public class OrderItemEntity implements java.io.Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	private ItemEntity item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private OrdersEntity order;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private int price;

	public OrderItemEntity() {
	}

	public OrderItemEntity(int id, ItemEntity item, OrdersEntity order, int quantity, int price) {
		this.id = id;
		this.item = item;
		this.order = order;
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

	public OrdersEntity getOrder() {
		return this.order;
	}

	public void setOrder(OrdersEntity order) {
		this.order = order;
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
