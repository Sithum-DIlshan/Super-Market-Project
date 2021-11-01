package bo.custom;

import bo.SuperBO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean adminLoginSuccess(String usrName, String pssWord) throws SQLException, ClassNotFoundException;

    boolean cashierLoginSuccess(String usrName, String pssWord) throws SQLException, ClassNotFoundException;
}
