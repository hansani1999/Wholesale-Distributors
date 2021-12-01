package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        //return CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?)",orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getOrderQty(),orderDetail.getPrice());

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(orderDetail);

        transaction.commit();
        session.close();

        return true;
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
        /*ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=? && itemCode=?", oId, itemCode);
        if(rst.next()){
            return new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }else {
            return null;
        }*/
        OrderDetail orderDetail = new OrderDetail();
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getOrderDetails(String oId) throws SQLException {
        /*ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=?", oId);
        ArrayList<OrderDetail> allOrderItemDetails = new ArrayList<>();

        while (rst.next()) {
            allOrderItemDetails.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return allOrderItemDetails;*/

        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        String hql = "FROM OrderDetail";
        Query query = session.createQuery(hql);
        List<OrderDetail> orderDetails = query.list();

        transaction.commit();
        session.close();

        return orderDetails;
    }

    @Override
    public boolean deleteOrderDetail(String oId, String itemCode) throws SQLException {
        /*return CrudUtil.executeUpdate("DELETE FROM `Order Detail` WHERE orderId=? && itemCode=?",oId, itemCode);*/
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM OrderDetail WHERE id = : order_id, ";
        Query query = session.createQuery(hql);
        List<OrderDetail> orderDetails = query.list();

        transaction.commit();
        session.close();*/

        return true;
    }

    @Override
    public boolean updateOrderDetail(OrderDetail orderDetail) throws SQLException {
        /*return CrudUtil.executeUpdate("UPDATE `Order Detail` SET OrderQty=?, price=? WHERE orderId=? && itemCode=?",orderDetail.getOrderQty(),orderDetail.getPrice(),orderDetail.getOrderId(),orderDetail.getItemCode());*/
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(orderDetail);

        transaction.commit();
        session.close();

        return true;
    }


}
