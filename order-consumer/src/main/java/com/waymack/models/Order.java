package com.waymack.models;

import java.util.Date;
import java.util.UUID;

public class Order {

    private String id;
    private String processingId;
    private String shippingId;
    private OrderStatus orderStatus;
    private String firstName;
    private String lastName;
    private Address address;
    private String email;
    private String item;
    private int quantity;
    private String totalCost;
    private Date purchaseTimestamp;
    private Date processingTimestamp;
    private Date shippingTimestamp;

    public void process() {
        processingId = UUID.randomUUID().toString();
        orderStatus = OrderStatus.Processing;
        processingTimestamp = new Date();
    }

    public void ship() {
        shippingId = UUID.randomUUID().toString();
        orderStatus = OrderStatus.Shipped;
        shippingTimestamp = new Date();
    }

    @Override
    public String toString() {
        String str;

        switch(orderStatus) {
            case Processing:
                str = "Processing order " + id +
                    ". Order placed at " + purchaseTimestamp.toString() +
                    " and processed at " + processingTimestamp.toString() + ".";
                break;
            case Shipped:
                str = "Shipped order " + id +
                    ". Order placed at " + purchaseTimestamp.toString() +
                    ", processed at " + processingTimestamp.toString() +
                    ", and shipped at " + shippingTimestamp + ".";
                break;
            default:
                str = "New order " + id +
                ". Order placed at " + purchaseTimestamp.toString() + ".";
        }

        return str;
    }
}