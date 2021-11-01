package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class OrderDTO implements Serializable {
    private String orderId;
    private Date orderDate;
    private String customerId;
    private ArrayList<OrderDetailsDTO> item;

    public OrderDTO(String orderId, Date orderDate, String customerId, ArrayList<OrderDetailsDTO> item) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.item = item;
    }

    public OrderDTO() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public java.util.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetailsDTO> getItem() {
        return item;
    }

    public void setItem(ArrayList<OrderDetailsDTO> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", item=" + item +
                '}';
    }
}
