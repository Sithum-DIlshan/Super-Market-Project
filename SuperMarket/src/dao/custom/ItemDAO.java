package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item, String> {
    public boolean updateItemQty(int orderQty, String itemCode) throws SQLException, ClassNotFoundException;

    boolean updateItemDescription(String itemCode, String itemDescription) throws SQLException, ClassNotFoundException;
}
