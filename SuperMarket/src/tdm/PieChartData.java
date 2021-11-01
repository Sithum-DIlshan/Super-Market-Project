package tdm;

public class PieChartData {
    private String itemCode;
    private int ttlQty;

    public PieChartData(String itemCode, int ttlQty) {
        this.itemCode = itemCode;
        this.ttlQty = ttlQty;
    }

    public PieChartData() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getTtlQty() {
        return ttlQty;
    }

    public void setTtlQty(int ttlQty) {
        this.ttlQty = ttlQty;
    }

    @Override
    public String toString() {
        return "PieChartData{" +
                "itemCode='" + itemCode + '\'' +
                ", ttlQty=" + ttlQty +
                '}';
    }

}
