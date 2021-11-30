package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    /*@Override
    public ArrayList<Customer> getAll() throws SQLException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst  = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()){
            allCustomers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return allCustomers;
    }

    @Override
    public Customer search(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM  Customer WHERE id =?",id);
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)",customer.getId(),customer.getTitle(),customer.getName(),customer.getAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode());
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE Customer SET title=?, name=?, address=?, city=?, province=?, postalCode=? WHERE id=?", customer.getTitle(),customer.getName(),customer.getAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode(),customer.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE id=?",id);
    }

    @Override
    public int getCustomerCount(String date) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(cId) FROM `ORDER` WHERE orderDate=?", date);

        if (rst.next()) {
            return rst.getInt(1);
        }else {
            return -1;
        }
    }

    @Override
    public boolean isExistsCustomer(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE id=?", id);
        if (rst.next()) {
            return true;
        }else {
            return false;
        }
    }*/

    @Override
    public boolean saveCustomer(Customer customer){
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        session.save(customer);

        transaction.commit();

        session.close();

        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer){
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        session.update(customer);

        transaction.commit();

        session.close();

        return true;
    }

    @Override
    public boolean deleteCustomer(String id){
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class,id);
        session.delete(customer);

        transaction.commit();

        session.close();

        return true;
    }

    @Override
    public List<Customer> getAllCustomers() {
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        String hql = "FROM Customer";
        Query query = session.createQuery(hql);
        List<Customer> allCustomers = query.list();

        transaction.commit();

        session.close();

        return allCustomers;
    }

    @Override
    public boolean isExists(String id){
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        String hql = "FROM Customer WHERE id = :customer_id";
        Query query = session.createQuery(hql);
        query.setParameter("customer_id",id);
        List<Customer> customerList= query.list();

        boolean ifExists =false;
        for (Customer customer : customerList) {
            if (id.equals(customer.getId())) {
                ifExists=true;
            }
        }

        transaction.commit();
        session.close();

        return ifExists;
    }

    @Override
    public Customer searchCustomer(String id){
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        Customer customer= session.get(Customer.class,id);

        transaction.commit();
        session.close();

        return customer;
    }

    public int getCustomerCount(String date) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(cId) FROM `ORDER` WHERE orderDate=?", date);


        if (rst.next()) {
            return rst.getInt(1);
        }else {
            return -1;
        }
    }

}
