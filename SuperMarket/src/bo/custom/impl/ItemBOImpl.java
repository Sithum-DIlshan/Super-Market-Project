package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.DiscountCodesDAO;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.DiscountCodes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final DiscountCodesDAO discountDAO = (DiscountCodesDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.DISCOUNT);

    @Override
    public boolean ItemSaved(ItemDTO item) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(item.getCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public ItemDTO search(String code) throws SQLException, ClassNotFoundException {
        if (itemDAO.search(code) != null) {
            Item search = itemDAO.search(code);
            return new ItemDTO(search.getCode(), search.getDescription(), search.getPackSize(), search.getUnitPrice(), search.getQtyOnHand());
        }
        return null;
    }

    @Override
    public boolean itemUpdated(ItemDTO item) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public boolean itemDeleted(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public ObservableList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ObservableList<String> obs = FXCollections.observableArrayList();

        for (Item item :
                all) {
            obs.add(item.getCode());
        }

        return obs;
    }

    @Override
    public double addDiscountToItem(String disId) throws SQLException, ClassNotFoundException {
        DiscountCodes search = discountDAO.search(disId);
        if (search != null) {
            return search.getDis_Price();
        }

        return -1;
    }

    @Override
    public boolean removeItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemCode);
    }

    @Override
    public boolean updateItemQty(int orderQty, String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.updateItemQty(orderQty, itemCode);
    }

    @Override
    public boolean updateItemDescription(String itemCode, String itemDescription) throws SQLException, ClassNotFoundException {
        return itemDAO.updateItemDescription(itemCode, itemDescription);
    }
}
