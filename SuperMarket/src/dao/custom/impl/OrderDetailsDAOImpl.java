package dao.custom.impl;

import dao.CrudUtil;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Item;
import entity.OrderDetails;
import tdm.ManageOrderTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    private ObservableList<ManageOrderTM> obs;
    ItemDAO item = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean add(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderDetails (itemCode, orderId, qty, discount) VALUES (?,?,?,?)", dto.getItemCode(), dto.getOrderId(), dto.getQty(), dto.getDiscount());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetails orderDetailDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetails search(String id) throws SQLException, ClassNotFoundException {

        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public int getNoOfItems(String orderId) throws SQLException, ClassNotFoundException {
        int count = 0;
        ResultSet rst =
                CrudUtil.executeQuery("SELECT * FROM OrderDetails WHERE orderId = ?", orderId);

        while (rst.next()) {
            ++count;
        }
        return count;
    }

    @Override
    public ObservableList<ManageOrderTM> getDataForManageOrder(String orderId) throws SQLException, ClassNotFoundException {
        obs = FXCollections.observableArrayList();
        ResultSet rst =
                CrudUtil.executeQuery("SELECT * FROM OrderDetails " +
                        "WHERE orderId = ?", orderId);

        // if (rst.next()) {
        while (rst.next()) {
            Item itemDetails = item.search(rst.getString(1));
            obs.add(new ManageOrderTM(
                    rst.getString(1),
                    itemDetails.getDescription(),
                    rst.getInt(3),
                    itemDetails.getUnitPrice()
            ));
            //   }


        }
        return obs;

    }

    @Override
    public boolean updateOrderDetailsByItemCode(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE OrderDetails SET qty = ? WHERE itemCode = ?", qty, itemCode);
    }

    @Override
    public ArrayList<OrderDetails> searchByItemCode(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetails WHERE itemCode = ?", code);
        ArrayList<OrderDetails> details = new ArrayList<>();
        while (rst.next()) {
            details.add(new OrderDetails(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4)));
        }

        return details;
    }

    @Override
    public ArrayList<OrderDetails> searchByOrderId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetails WHERE orderId = ?", id);
        /*rst.next();
        return new OrderDetails(rst.getString(1), id, rst.getInt(3), rst.getDouble(4));
   */
        ArrayList<OrderDetails> details = new ArrayList<>();
        while (rst.next()) {
            details.add(new OrderDetails(rst.getString(1), id, rst.getInt(3), rst.getDouble(4)));
        }

        return details;
    }
}
