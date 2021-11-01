package tdm;

public class AddToCartTM {
    private String itemCode;
    private String itemDescription;
    private String customerId;
    private double itemUnitPrice;
    private int orderedQty;
    private double ttl;

    public AddToCartTM(String itemCode, String itemDescription, String customerId, double itemUnitPrice, int orderedQty, double ttl) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.customerId = customerId;
        this.itemUnitPrice = itemUnitPrice;
        this.orderedQty = orderedQty;
        this.ttl = ttl;
    }

    public AddToCartTM() {

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

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public int getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(int orderedQty) {
        this.orderedQty = orderedQty;
    }

    public double getTtl() {
        return ttl;
    }

    public void setTtl(double ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "AddToCartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemUnitPrice=" + itemUnitPrice +
                ", orderedQty=" + orderedQty +
                ", ttl=" + ttl +
                '}';
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
