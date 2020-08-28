package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)

public class OrderController {

    /**
     * The Order service.
     */
    @Autowired
    OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

    /**
     * Gets coupons
     *
     * @param authorization the authorization
     * @param couponName    the coupon name
     * @return the coupon
     * @throws AuthorizationFailedException the authorization failed exception
     * @throws CouponNotFoundException      the coupon not found exception
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCoupon(@RequestHeader("authorization") final String authorization,
                                                           @PathVariable("coupon_name") String couponName) throws AuthorizationFailedException, CouponNotFoundException {

        String accessToken = authorization.split("Bearer ")[1];
        customerService.getCustomer(accessToken);
        CouponEntity coupon = orderService.getCouponByCouponName(couponName);
        CouponDetailsResponse custDetailsRes = new CouponDetailsResponse()
                .couponName(coupon.getCouponName())
                .id(UUID.fromString(coupon.getUuid()))
                .percent(coupon.getPercent());

        return new ResponseEntity<>(custDetailsRes, HttpStatus.OK);

    }

    /**
     * Gets orders by customer.
     *
     * @param authorization the authorization
     * @return the orders by customer
     * @throws AuthorizationFailedException the authorization failed exception
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getOrdersByCustomer(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);
        List<OrderList> orderLists = new LinkedList<>();
        List<OrderEntity> allOrders = orderService.getOrdersByCustomers(customerEntity.getUuid());

        //For every orders by the customer retrieve the details related to order like payment, items, coupon etc
        allOrders.forEach(order -> {
                    List<OrderItemEntity> orderItemEntity = orderService.getOrderItems(order);
                    List<ItemQuantityResponse> itemQtyRes = new ArrayList<>();
                    orderItemEntity.forEach(orderItem -> {
                                ItemQuantityResponseItem itemQtyItem = new ItemQuantityResponseItem();
                                itemQtyItem.id(UUID.fromString(orderItem.getItem().getUuid()))
                                        .itemName(orderItem.getItem().getItemName())
                                        .type(ItemQuantityResponseItem.TypeEnum.values()[Integer.parseInt(orderItem.getItem().getType())])
                                        .itemPrice(orderItem.getItem().getPrice());

                                itemQtyRes.add(new ItemQuantityResponse()
                                        .item(itemQtyItem)
                                        .quantity(orderItem.getQuantity())
                                        .price(orderItem.getPrice())
                                );
                            }
                    );

                    //Create response object for displaying back
                    OrderList orderList = new OrderList()
                            .id(UUID.fromString(order.getUuid()))
                            .bill(order.getBill())
                            .coupon(new OrderListCoupon()
                                    .couponName(order.getCouponEntity().getCouponName())
                                    .id(UUID.fromString(order.getCouponEntity().getUuid())))
                            .discount(order.getDiscount())
                            .date(order.getDate().toString())
                            .payment(new OrderListPayment()
                                    .id(UUID.fromString(order.getPaymentEntity().getUuid()))
                                    .paymentName(order.getPaymentEntity().getPaymentName()))
                            .customer(new OrderListCustomer()
                                    .id(UUID.fromString(order.getCustomer().getUuid()))
                                    .contactNumber(order.getCustomer().getContact_number())
                                    .firstName(order.getCustomer().getFirstname())
                                    .lastName(order.getCustomer().getLastname())
                                    .emailAddress(order.getCustomer().getEmail()))
                            .address(new OrderListAddress()
                                    .id(UUID.fromString(order.getAddress().getUuid()))
                                    .flatBuildingName(order.getAddress().getUuid())
                                    .locality(order.getAddress().getUuid())
                                    .city(order.getAddress().getUuid())
                                    .pincode(order.getAddress().getUuid())
                                    .state(new OrderListAddressState()
                                            .id((UUID.fromString(order.getAddress().getState().getUuid())))
                                            .stateName(order.getAddress().getState().getStateName())
                                    ))
                            .itemQuantities(itemQtyRes);
                    orderLists.add(orderList);
                }
        );

        return new ResponseEntity<>(new CustomerOrderResponse().orders(orderLists), HttpStatus.OK);

    }

    /**
     * Create new Order.
     *
     * @param authorization    the authorization
     * @param saveOrderRequest the save order request
     * @return the response entity
     * @throws AuthorizationFailedException   the authorization failed exception
     * @throws CategoryNotFoundException      the category not found exception
     * @throws CouponNotFoundException        the coupon not found exception
     * @throws PaymentMethodNotFoundException the payment method not found exception
     * @throws RestaurantNotFoundException    the restaurant not found exception
     * @throws ItemNotFoundException          the item not found exception
     * @throws AddressNotFoundException       the address not found exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(@RequestHeader("authorization") final String authorization, @RequestBody final SaveOrderRequest saveOrderRequest)
            throws AuthorizationFailedException, CategoryNotFoundException, CouponNotFoundException, PaymentMethodNotFoundException, RestaurantNotFoundException, ItemNotFoundException, AddressNotFoundException {

        String authToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(authToken);

        //Initially create an order entity with all prerequisites
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setCouponEntity(orderService.getCouponByCouponId(saveOrderRequest.getCouponId().toString()));
        orderEntity.setPaymentEntity(paymentService.getPaymentByUUID(saveOrderRequest.getPaymentId().toString()));
        orderEntity.setCustomer(customerEntity);
        orderEntity.setAddress(addressService.getAddressByUUID(saveOrderRequest.getAddressId(), customerEntity));
        orderEntity.setBill(saveOrderRequest.getBill());
        orderEntity.setDiscount(saveOrderRequest.getDiscount());
        orderEntity.setRestaurant(restaurantService.restaurantByUUID(saveOrderRequest.getRestaurantId().toString()));
        orderEntity.setDate(new Date());
        OrderEntity savedOrderEntity = orderService.saveOrder(orderEntity);

        //For previously created order add item details
        for (ItemQuantity itemQuantity : saveOrderRequest.getItemQuantities()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(savedOrderEntity);
            orderItemEntity.setItem(itemService.getItemByUUID(itemQuantity.getItemId().toString()));
            orderItemEntity.setQuantity(itemQuantity.getQuantity());
            orderItemEntity.setPrice(itemQuantity.getPrice());
            orderService.saveOrderItem(orderItemEntity);
        }

        SaveOrderResponse saveOrderResponse = new SaveOrderResponse()
                .id(savedOrderEntity.getUuid())
                .status("ORDER SUCCESSFULLY PLACED");

        return new ResponseEntity<>(saveOrderResponse, HttpStatus.CREATED);
    }
}