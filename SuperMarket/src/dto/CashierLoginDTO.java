package dto;

public class CashierLoginDTO {
    private String cashierUsrName;
    private String cashierPassword;

    public CashierLoginDTO(String cashierUsrName, String cashierPassword) {
        this.cashierUsrName = cashierUsrName;
        this.cashierPassword = cashierPassword;
    }

    public CashierLoginDTO() {
    }

    public String getCashierUsrName() {
        return cashierUsrName;
    }

    public void setCashierUsrName(String cashierUsrName) {
        this.cashierUsrName = cashierUsrName;
    }

    public String getCashierPassword() {
        return cashierPassword;
    }

    public void setCashierPassword(String cashierPassword) {
        this.cashierPassword = cashierPassword;
    }

    @Override
    public String toString() {
        return "CashierLogin{" +
                "cashierUsrName='" + cashierUsrName + '\'' +
                ", cashierPassword='" + cashierPassword + '\'' +
                '}';
    }
}
