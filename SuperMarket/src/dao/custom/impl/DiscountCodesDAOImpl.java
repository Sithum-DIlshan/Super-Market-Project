package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.DiscountCodesDAO;
import entity.DiscountCodes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountCodesDAOImpl implements DiscountCodesDAO {
    @Override
    public boolean add(DiscountCodes discountCodes) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(DiscountCodes discountCodes) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public DiscountCodes search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM DiscountCodes WHERE dis_Code = ?", s);
        if (rst.next()) {
            return new DiscountCodes(rst.getString(1), rst.getDouble(2));
        }
        return null;
    }

    @Override
    public ArrayList<DiscountCodes> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
