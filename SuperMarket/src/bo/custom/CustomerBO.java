package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    boolean customerSaved(CustomerDTO c) throws SQLException, ClassNotFoundException;

    ObservableList<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    CustomerDTO getCustomerDetails(String custID) throws SQLException, ClassNotFoundException;
}
