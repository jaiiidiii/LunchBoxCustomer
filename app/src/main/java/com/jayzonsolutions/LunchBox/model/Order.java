package com.jayzonsolutions.LunchBox.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Order {

    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderCustomerId")
    @Expose
    private Integer orderCustomerId;
    @SerializedName("orderReservationId")
    @Expose
    private Object orderReservationId;
    @SerializedName("foodmakerId")
    @Expose
    private Integer foodmakerId;
    @SerializedName("orderShipmentAddress")
    @Expose
    private String orderShipmentAddress;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("orderDeliverDate")
    @Expose
    private String orderDeliverDate;
    @SerializedName("orderTotalAmount")
    @Expose
    private Integer orderTotalAmount;
    @SerializedName("orderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("orderdishes")
    @Expose
    private List<OrderDish> orderdishes;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("foodmaker")
    @Expose
    private Foodmaker foodmaker;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(Integer orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public Object getOrderReservationId() {
        return orderReservationId;
    }

    public void setOrderReservationId(Object orderReservationId) {
        this.orderReservationId = orderReservationId;
    }

    public Integer getFoodmakerId() {
        return foodmakerId;
    }

    public void setFoodmakerId(Integer foodmakerId) {
        this.foodmakerId = foodmakerId;
    }

    public String getOrderShipmentAddress() {
        return orderShipmentAddress;
    }

    public void setOrderShipmentAddress(String orderShipmentAddress) {
        this.orderShipmentAddress = orderShipmentAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDeliverDate() {
        return orderDeliverDate;
    }

    public void setOrderDeliverDate(String orderDeliverDate) {
        this.orderDeliverDate = orderDeliverDate;
    }

    public Integer getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(Integer orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDish> getOrderdishes() {
        return orderdishes;
    }

    public void setOrderdishes(List<OrderDish> orderdishes) {
        this.orderdishes = orderdishes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Foodmaker getFoodmaker() {
        return foodmaker;
    }

    public void setFoodmaker(Foodmaker foodmaker) {
        this.foodmaker = foodmaker;
    }

}
