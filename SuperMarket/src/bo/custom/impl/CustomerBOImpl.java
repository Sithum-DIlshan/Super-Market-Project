package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean customerSaved(CustomerDTO c) throws SQLException, ClassNotFoundException {
        /*con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)");
        stm.setObject(1, c.getId());
        stm.setObject(2, c.getTitle());
        stm.setObject(3, c.getName());
        stm.setObject(4, c.getAddress());
        stm.setObject(5, c.getCity());
        stm.setObject(6, c.getProvince());
        stm.setObject(7, c.getPostalCode());

        return stm.executeUpdate() > 0;*/
        return customerDAO.add(new Customer(c.getId(), c.getTitle(), c.getName(), c.getAddress(), c.getCity(), c.getProvince(), c.getPostalCode()));
    }

    @Override
    public ObservableList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ObservableList<String> obs = FXCollections.observableArrayList();

        if (all != null) {
            for (Customer customer :
                    all) {
                obs.add(customer.getCustId());
            }
            return obs;
        }
        return null;
    }

    @Override
    public CustomerDTO getCustomerDetails(String custID) throws SQLException, ClassNotFoundException {
        Customer search = customerDAO.search(custID);
        return new CustomerDTO(search.getCustId(), search.getCustTitle(), search.getName(), search.getAddress(), search.getCity(), search.getProvince(), search.getPostalCode());
    }
}
