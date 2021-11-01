package tdm;

public class ManageOrderTM {
    private String itemCode;
    private String itemDescription;
    private int orderQty;
    private double unitPrice;

    public ManageOrderTM(String itemCode, String itemDescription, int orderQty, double unitPrice) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
    }

    public ManageOrderTM() {

    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ManageOrderTM{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", orderQty=" + orderQty +
                ", unitPrice=" + unitPrice +
                '}';
    }

}
