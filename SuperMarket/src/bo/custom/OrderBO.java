package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Item;
import javafx.collections.ObservableList;
import tdm.ManageOrderTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    String getOrderId() throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDTO order) throws SQLException;

    boolean saveOrderDetails(String orderId, ArrayList<OrderDetailsDTO> items) throws SQLException, ClassNotFoundException;

    boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException;

    ObservableList<String> getMngOrderOrderIds(String cId) throws SQLException, ClassNotFoundException;

    int getNoOfItems(String orderId) throws SQLException, ClassNotFoundException;

    ObservableList<ManageOrderTM> getDataForTable(String orderId) throws SQLException, ClassNotFoundException;

    Item getItemDescriptionAndUnitPrice(String itemCode) throws SQLException, ClassNotFoundException;

    /*private String getItemDescription(String itemCode) throws SQLException, ClassNotFoundException {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE code = '" + itemCode + "'").executeQuery();
            if (rst.next()) {
                return rst.getString(2);
            } else {
                return null;
            }
        }
    */
    boolean removeOrder(String orderId) throws SQLException, ClassNotFoundException;

    boolean removeOrderAndUpdateQty(String orderId) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetail(String itemCode, Integer qty) throws SQLException, ClassNotFoundException;

    ArrayList<String> getItemOrderQuantities() throws SQLException, ClassNotFoundException;

    int getTotalQty(String code) throws SQLException, ClassNotFoundException;
}
