package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import db.DbConnection;
import dto.AdminLoginDTO;
import dto.CashierLoginDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET qtyOnHand = (qtyOnHand - " + qty + ")" + "WHERE code = ?", code);
    }

    @Override
    public AdminLoginDTO getAdmin() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM AdminLogin");
        rst.next();
        return new AdminLoginDTO(rst.getString(1), rst.getString(2));
    }

    @Override
    public CashierLoginDTO getCashier() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM CashierLogin");
        rst.next();
        return new CashierLoginDTO(rst.getString(1), rst.getString(2));
    }

    @Override
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
