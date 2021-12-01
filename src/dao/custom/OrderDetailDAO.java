package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String> {
    public OrderDetail searchOrderDetail(String oId,String itemCode) throws SQLException;
    public List<OrderDetail> getOrderDetails(String oId) throws SQLException;
    public boolean deleteOrderDetail(String oId,String itemCode) throws SQLException;
    public boolean updateOrderDetail(OrderDetail orderDetail) throws SQLException;
}
