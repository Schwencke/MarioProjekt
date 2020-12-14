package domain;

import java.util.Date;

public class Orders {

    private int orderID;
    private int pizzaNo;
    private int amount;
    private int pickupTime;
    private Date orderTime;
    private String customerName;
    private String phoneNo;
    private boolean removed;

    public Orders(int pizzaNo, int amount, int pickupTime, String customerName, String phoneNo) {
        this.pizzaNo = pizzaNo;
        this.amount = amount;
        this.pickupTime = pickupTime;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public Orders(int orderID, int pizzaNo, int amount, int pickupTime, Date orderTime, String customerName, String phoneNo, boolean removed) {
        this.orderID = orderID;
        this.pizzaNo = pizzaNo;
        this.amount = amount;
        this.pickupTime = pickupTime;
        this.orderTime = orderTime;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.removed = removed;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderID=" + orderID +
                ", pizzaNo=" + pizzaNo +
                ", amount=" + amount +
                ", pickupTime=" + pickupTime +
                ", orderTime=" + orderTime +
                ", customerName='" + customerName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", removed=" + removed +
                '}';
    }
}
