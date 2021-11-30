package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;


public interface CustomerDAO  extends SuperDAO/*extends CrudDAO<Customer,String>*/ {
    /*public int getCustomerCount(String date) throws SQLException;
    public boolean isExistsCustomer(String id) throws SQLException;*/
    public boolean saveCustomer(Customer customer);
    public boolean updateCustomer(Customer customer);
    public boolean deleteCustomer(String id);
    public List<Customer> getAllCustomers();
    public boolean isExists(String id);
    public Customer searchCustomer(String id);
}
