package com.upgrad.FoodOrderingApp.service.entity;// default package

import javax.persistence.*;

@Entity
@Table(name = "payment", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
@NamedQueries({
        @NamedQuery(name = "allPaymentOptions", query = "select p from PaymentEntity p"),
        @NamedQuery(name ="getPaymentByUUID", query = "select p from PaymentEntity p where p.uuid =:paymentUUID")


})
public class PaymentEntity implements java.io.Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", unique = true, nullable = false, length = 200)
    private String uuid;

    @Column(name = "payment_name")
    private String paymentName;

    public PaymentEntity() {
    }

    public PaymentEntity(String uuid, String paymentName) {
        this.uuid = uuid;
        this.paymentName = paymentName;
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

    public String getPaymentName() {
        return this.paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

}
