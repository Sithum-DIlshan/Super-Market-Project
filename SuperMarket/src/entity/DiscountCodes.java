package entity;

public class DiscountCodes {
    private String dis_Code;
    private double dis_Price;

    public DiscountCodes(String dis_Code, double dis_Price) {
        this.dis_Code = dis_Code;
        this.dis_Price = dis_Price;
    }

    public DiscountCodes() {
    }

    public String getDis_Code() {
        return dis_Code;
    }

    public void setDis_Code(String dis_Code) {
        this.dis_Code = dis_Code;
    }

    public double getDis_Price() {
        return dis_Price;
    }

    public void setDis_Price(double dis_Price) {
        this.dis_Price = dis_Price;
    }

    @Override
    public String toString() {
        return "DiscountCodes{" +
                "dis_Code='" + dis_Code + '\'' +
                ", dis_Price=" + dis_Price +
                '}';
    }
}
