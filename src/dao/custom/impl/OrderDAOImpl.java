package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");

        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            ));
        }
        return orders;
    }

    @Override
    public Order search(String id) throws SQLException {
        ResultSet rst =  CrudUtil.executeQuery("SELECT * FROM `Order` WHERE orderId=?",id);
        if (rst.next()) {
            return new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            );
        }else {
            return null;
        }
    }

    @Override
    public boolean save(Order order) throws SQLException {
        return CrudUtil.executeUpdate("INSERT  INTO `Order` VALUES (?,?,?,?,?,?)",order.getOrderId(),order.getcId(),order.getOrderDate(),order.getTime(),order.getCost(),order.getDiscount());
    }

    @Override
    public boolean update(Order order) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE `Order`SET cost=?, discount=? WHERE orderId=?",order.getCost(),order.getDiscount(),order.getOrderId());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE orderId=?",id);
    }

    @Override
    public ArrayList<Order> getCustomerOrder(String cId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE cId=?", cId);
        ArrayList<Order> allOrdersOfCustomer = new ArrayList<>();
        while (rst.next()) {
             allOrdersOfCustomer.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            ));
        }
        return allOrdersOfCustomer;
    }

    @Override
    public String generateOrderId() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");

        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<=99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

    @Override
    public double getTodayIncome(String date) throws SQLException {
       ResultSet rst =  CrudUtil.executeQuery("SELECT SUM(cost) FROM `Order` WHERE orderDate=?",date);
        if (rst.next()) {
            return rst.getDouble(1);
        }else {
            return -1;
        }

    }

    @Override
    public double getTotalIncome() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(cost) FROM `Order`");
        if (rst.next()) {
            return rst.getDouble(1);
        }else {
            return -1;
        }
    }

}
