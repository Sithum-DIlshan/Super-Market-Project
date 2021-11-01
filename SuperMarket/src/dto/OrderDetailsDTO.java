package dto;

import java.io.Serializable;

public class OrderDetailsDTO implements Serializable {
    private String oId;
    private String itemCode;
    private int orderedQty;
    private double discount;

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public OrderDetailsDTO(String oId, String itemCode, int orderedQty, double discount) {
        this.oId = oId;
        this.itemCode = itemCode;
        this.orderedQty = orderedQty;
        this.discount = discount;
    }

    public OrderDetailsDTO() {

    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(int orderedQty) {
        this.orderedQty = orderedQty;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", orderedQty=" + orderedQty +
                '}';
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
