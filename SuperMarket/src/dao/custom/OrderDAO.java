package dao.custom;

import dao.CrudDAO;
import javafx.collections.ObservableList;
import entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order,String> {
    String getOrderId() throws SQLException, ClassNotFoundException;

    ObservableList<String> getOrdersForCustomer(String cId) throws SQLException, ClassNotFoundException;
}
