package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomers() throws SQLException;

    boolean saveCustomer(CustomerDTO customer) throws SQLException;

    boolean updateCustomer(CustomerDTO customer) throws SQLException;

    boolean deleteCustomer(String text) throws SQLException;

    boolean ifExistsCustomer(String id) throws SQLException;
}
