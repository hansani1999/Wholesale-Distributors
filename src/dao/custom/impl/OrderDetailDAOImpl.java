package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException {
        return null;
    }

    @Override
    public OrderDetail search(String oId) throws SQLException {
       return null;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?)",orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getOrderQty(),orderDetail.getPrice());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public OrderDetail searchOrderDetail(String oId,String itemCode) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=? && itemCode=?", oId, itemCode);
        if(rst.next()){
            return new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetails(String oId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=?", oId);
        ArrayList<OrderDetail> allOrderItemDetails = new ArrayList<>();

        while (rst.next()) {
            allOrderItemDetails.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return allOrderItemDetails;
    }

    @Override
    public boolean deleteOrderDetail(String oId, String itemCode) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM `Order Detail` WHERE orderId=? && itemCode=?",oId, itemCode);
    }

    @Override
    public boolean updateOrderDetail(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE `Order Detail` SET OrderQty=?, price=? WHERE orderId=? && itemCode=?",orderDetail.getOrderQty(),orderDetail.getPrice(),orderDetail.getOrderId(),orderDetail.getItemCode());
    }


}
