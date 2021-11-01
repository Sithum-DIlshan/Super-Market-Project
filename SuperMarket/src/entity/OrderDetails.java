package entity;

public class OrderDetails {
    private String itemCode;
    private String orderId;
    private int qty;
    private double discount;

    public OrderDetails(String itemCode, String orderId, int qty, double discount) {
        this.itemCode = itemCode;
        this.orderId = orderId;
        this.qty = qty;
        this.discount = discount;
    }

    public OrderDetails() { }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                '}';
    }
}
