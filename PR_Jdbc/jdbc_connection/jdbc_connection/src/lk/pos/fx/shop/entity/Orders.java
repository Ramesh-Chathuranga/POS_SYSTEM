package lk.pos.fx.shop.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Orders {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private ArrayList<OrderDetail> orderDetails=new ArrayList<>();

    public Orders() {
    }

    public Orders(String orderId, LocalDate date, String customerId, ArrayList<OrderDetail> orderDetails) {
        this.setOrderId(orderId);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setOrderDetails(orderDetails);
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
