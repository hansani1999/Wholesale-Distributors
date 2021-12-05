package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order,String> {
    boolean saveOrder(Order order, String cId) throws SQLException;
    ArrayList<Order> getCustomerOrder(String cId) throws SQLException;
    public String generateOrderId() throws SQLException ;
    public double getTodayIncome(String date) throws SQLException;
    public double getTotalIncome() throws SQLException;
}
