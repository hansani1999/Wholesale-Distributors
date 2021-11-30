package bo.custom.impl;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.impl.*;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    //CustomerDAO customerDAO = new CustomerDAOImpl();
    //CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        List<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : customerDAO.getAllCustomers()) {
            allCustomers.add(new CustomerDTO(
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customer) throws SQLException {
        return customerDAO.saveCustomer(new Customer(customer.getCustomerId(),customer.getTitle(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customer) throws SQLException {
        return customerDAO.updateCustomer(new Customer(customer.getCustomerId(),customer.getTitle(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.deleteCustomer(id);
    }

    @Override
    public boolean ifExistsCustomer(String id) throws SQLException {
        return customerDAO.isExists(id);
    }
}
