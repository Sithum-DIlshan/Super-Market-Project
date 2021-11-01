package entity;


import java.util.Date;

public class Order {
    private String orderId;
    private Date orderDate;
    private String cid;

    public Order(String orderId, Date orderDate, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cid = customerId;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return (Date) orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + cid + '\'' +
                '}';
    }
}
