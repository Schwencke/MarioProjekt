package domain;

import java.util.Date;

public class Statistics {
    private int orderId;
    private int pizzaNo;
    private int amount;
    private int pickupTime;
    private Date orderTime;
    private String phone;

    public Statistics(int orderId, int pizzaNo, int amount, int pickupTime, Date orderTime, String phone) {
        this.orderId = orderId;
        this.pizzaNo = pizzaNo;
        this.amount = amount;
        this.pickupTime = pickupTime;
        this.orderTime = orderTime;
        this.phone = phone;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPizzaNo() {
        return pizzaNo;
    }

    public void setPizzaNo(int pizzaNo) {
        this.pizzaNo = pizzaNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(int pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
