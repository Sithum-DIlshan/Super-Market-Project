package bo;

import bo.SuperBO;
import bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {

    }

    public static BoFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case LOGIN:
                return new LoginBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PIE_CHART:
                return new PieChartBOImpl();
        }
        return null;
    }

    public enum BoTypes {
        LOGIN, CUSTOMER, ITEM, ORDER, PIE_CHART
    }
}
