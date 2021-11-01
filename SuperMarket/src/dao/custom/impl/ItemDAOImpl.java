package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)", item.getCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE code = ?", code);
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE code=?", item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(), item.getCode());
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst =
                CrudUtil.executeQuery("SELECT * FROM Item WHERE code = ?", code);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5));
        }
       return null;

    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(rst.getString("code"), rst.getString("description"), rst.getString("packSize"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));
        }
        return allItems;
    }

    @Override
    public boolean updateItemQty(int orderQty, String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET qtyOnHand = (qtyOnHand +'" + orderQty + "') WHERE code = ?", itemCode);
    }

    @Override
    public boolean updateItemDescription(String itemCode, String itemDescription) throws SQLException, ClassNotFoundException {
        /*int i =
                DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET " +
                        "description = '" + itemDescription + "'" + "WHERE code = '" + itemCode + "'").executeUpdate();

        return i > 0;*/

        return CrudUtil.executeUpdate("UPDATE Item SET description = ? WHERE code = ?", itemDescription, itemCode);
    }
}
