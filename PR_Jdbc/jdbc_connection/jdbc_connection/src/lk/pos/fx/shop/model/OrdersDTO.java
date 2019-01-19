package lk.pos.fx.shop.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersDTO {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private ArrayList<OrderDetailDTO> orderDetailDTOS=new ArrayList<>();

    public OrdersDTO() {
    }

    public OrdersDTO(String orderId, LocalDate date, String customerId, ArrayList<OrderDetailDTO> orderDetailDTOS) {
        this.setOrderId(orderId);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setOrderDetailDTOS(orderDetailDTOS);
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

    public ArrayList<OrderDetailDTO> getOrderDetailDTOS() {
        return orderDetailDTOS;
    }

    public void setOrderDetailDTOS(ArrayList<OrderDetailDTO> orderDetailDTOS) {
        this.orderDetailDTOS = orderDetailDTOS;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", orderDetailDTOS=" + orderDetailDTOS +
                '}';
    }
}
