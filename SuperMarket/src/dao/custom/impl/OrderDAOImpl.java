package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES (?,?,?)", order.getOrderId(), order.getOrderDate(), order.getCid());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE orderId = ?", s);
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");
        while (rst.next()) {
            allOrders.add(new Order(rst.getString(1), rst.getDate(2), rst.getString(3)));
        }
        return allOrders;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");

        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "O-00" + tempId;
            } else if (tempId < 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }
    }

    @Override
    public ObservableList<String> getOrdersForCustomer(String cId) throws SQLException, ClassNotFoundException {
        ObservableList<String> obs = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order`WHERE cId = ?", cId);
        while (rst.next()) {
            obs.add(rst.getString(1));
        }
        return obs;
    }
}
