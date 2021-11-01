package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.QueryDAO;
import db.DbConnection;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Item;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.OrderDetails;
import tdm.ManageOrderTM;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class OrderBOImpl implements OrderBO {
    //  ObservableList<ManageOrderTM> obs;
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        String lastOrderId = queryDAO.getLastOrderId();

        if (lastOrderId != null) {

            int tempId = Integer.
                    parseInt(lastOrderId.split("-")[1]);
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
    public boolean saveOrder(OrderDTO order) throws SQLException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            /*PreparedStatement stm = con.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            stm.setObject(1, order.getOrderId());
            stm.setObject(2, order.getOrderDate());
            stm.setObject(3, order.getCid());
            */
            if (orderDAO.add(new Order(order.getOrderId(), order.getOrderDate(), order.getCustomerId()))) {
                if (saveOrderDetails(order.getOrderId(), order.getItem())) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            con.setAutoCommit(true);
        }

        return false;
    }

    @Override
    public boolean saveOrderDetails(String orderId, ArrayList<OrderDetailsDTO> items) throws SQLException, ClassNotFoundException {
        for (OrderDetailsDTO temp :
                items) {
            if (orderDetailsDAO.add(new OrderDetails(temp.getItemCode(), orderId, temp.getOrderedQty(), temp.getDiscount()))) {
                return updateQty(temp.getItemCode(), temp.getOrderedQty());
            } else {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Item SET qtyOnHand = (qtyOnHand - " + qty + ")" +
                        " WHERE code = '" + code + "'");

        return stm.executeUpdate() > 0;*/
        return itemDAO.updateItemQty(qty, code);
    }

    @Override
    public ObservableList<String> getMngOrderOrderIds(String cId) throws SQLException, ClassNotFoundException {
        ObservableList<String> obs = FXCollections.observableArrayList();
        ArrayList<Order> all = orderDAO.getAll();
        for (Order order :
                all) {
            obs.add(order.getOrderId());
        }
        return obs;
    }

    @Override
    public int getNoOfItems(String orderId) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.getNoOfItems(orderId);
    }

    @Override
    public ObservableList<ManageOrderTM> getDataForTable(String orderId) throws SQLException, ClassNotFoundException {
        ObservableList<ManageOrderTM> obs = FXCollections.observableArrayList();
        ObservableList<ManageOrderTM> dataForManageOrder = orderDetailsDAO.getDataForManageOrder(orderId);

        for (ManageOrderTM tm :
                dataForManageOrder) {
            Item item = getItemDescriptionAndUnitPrice(tm.getItemCode());
            obs.add(new ManageOrderTM(
                    tm.getItemCode(),
                    item.getDescription(),
                    tm.getOrderQty(),
                    item.getUnitPrice()
            ));
        }

        return obs;
    }

    @Override
    public Item getItemDescriptionAndUnitPrice(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.search(itemCode);
    }

    /*private String getItemDescription(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE code = '" + itemCode + "'").executeQuery();
        if (rst.next()) {
            return rst.getString(2);
        } else {
            return null;
        }
    }
*/
    @Override
    public boolean removeOrder(String orderId) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(orderId);
    }

    @Override
    public boolean removeOrderAndUpdateQty(String orderId) throws SQLException, ClassNotFoundException {
        /*ResultSet rst =
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order Detail` " +
                        "WHERE orderId = '" + orderId + "'").executeQuery();*/

        ArrayList<OrderDetails> search = orderDetailsDAO.searchByOrderId(orderId);


        /*while (rst.next()) {
            new ItemBOImpl().updateItemQty(rst.getInt(3), rst.getString(1));
        }*/

        for (OrderDetails details :
                search) {
            itemDAO.updateItemQty(details.getQty(), details.getItemCode());
        }

        /*int i =
                DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order` " +
                        "WHERE orderId = '" + orderId + "'").executeUpdate();

        return i > 0;*/
        return orderDAO.delete(orderId);
    }

    @Override
    public boolean updateOrderDetail(String itemCode, Integer qty) throws SQLException, ClassNotFoundException {
        /*int i =
                DbConnection.getInstance().getConnection().prepareStatement(
                        "UPDATE `Order Detail` SET qty = '" + qty + "'" +
                                "WHERE itemCode = '" + itemCode + "'").executeUpdate();

        return i > 0;*/

        return orderDetailsDAO.updateOrderDetailsByItemCode(itemCode, qty);
    }

    @Override
    public ArrayList<String> getItemOrderQuantities() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<String> ids = new ArrayList<>();
        for (Item item :
                all) {
            ids.add(item.getCode());
        }
        return ids;
    }

    @Override
    public int getTotalQty(String code) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = orderDetailsDAO.searchByItemCode(code);
        int ttl = 0;
        for (OrderDetails details :
                all) {
            ttl += details.getQty();
        }
        return ttl;
    }
}

