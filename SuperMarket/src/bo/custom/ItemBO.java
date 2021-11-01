package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import javafx.collections.ObservableList;
import entity.Item;

import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    boolean ItemSaved(dto.ItemDTO item) throws SQLException, ClassNotFoundException;

    ItemDTO search(String code) throws SQLException, ClassNotFoundException;

    boolean itemUpdated(dto.ItemDTO item) throws SQLException, ClassNotFoundException;

    boolean itemDeleted(String code) throws SQLException, ClassNotFoundException;

    ObservableList<String> getItemIds() throws SQLException, ClassNotFoundException;

    double addDiscountToItem(String disId) throws SQLException, ClassNotFoundException;

    boolean removeItem(String itemCode) throws SQLException, ClassNotFoundException;

    boolean updateItemQty(int orderQty, String itemCode) throws SQLException, ClassNotFoundException;

    boolean updateItemDescription(String itemCode, String itemDescription) throws SQLException, ClassNotFoundException;
}
