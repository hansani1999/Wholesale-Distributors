package bo.custom.impl;

import bo.custom.ManageOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import dao.custom.impl.*;
import db.DbConnection;
import dto.CustomDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import javafx.scene.control.Alert;
import views.tm.OrderItemTm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private OrderDAO orderDAO = (OrderDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private CustomerDAO customerDAO = (CustomerDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private OrderDetailDAO orderDetailDAO = (OrderDetailDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    private QueryDAO queryDAO = (QueryDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);


    @Override
    public ArrayList<CustomDTO> getOrderItemDetails(String oId) throws SQLException {
        ArrayList<CustomDTO> detailDTOS = queryDAO.getOrderItemDetails(oId);
        return detailDTOS;
    }

    @Override
    public OrderDTO searchOrder(String oId) throws SQLException {
        Order order = orderDAO.search(oId);
        return new OrderDTO(order.getOrderId(),order.getcId(),order.getOrderDate(),order.getTime(),order.getCost(),order.getDiscount());
    }

    @Override
    public boolean deleteOrder(String oId) throws SQLException {
        return orderDAO.delete(oId);
    }


    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : itemDAO.getAll()) {
            allItems.add(new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()
            ));
        }
        return allItems;
    }

    @Override
    public ArrayList<OrderDTO> getCustomerOrder(String oId) throws SQLException {
        ArrayList<OrderDTO> customerOrders = new ArrayList<>();
        for (Order order : orderDAO.getCustomerOrder(oId)) {
            customerOrders.add(new OrderDTO(
                    order.getOrderId(),
                    order.getcId(),
                    order.getOrderDate(),
                    order.getTime(),
                    order.getCost(),
                    order.getCost()
            ));
        }
        return customerOrders;
    }

    @Override
    public OrderDetailDTO searchOrderDetail(String oId, String itemCode) throws SQLException {
        OrderDetail orderDetail = orderDetailDAO.searchOrderDetail(oId,itemCode);
        //return new OrderDetailDTO(orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getOrderQty(),orderDetail.getPrice());

        return new OrderDetailDTO();
    }

    @Override
    public ItemDTO searchItem(String itemCode) throws SQLException {
        Item item = itemDAO.search(itemCode);
        return new ItemDTO(item.getCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice());
    }

    @Override
    public boolean updateOrderForDelete(CustomDTO temp, OrderDTO orderEdit) {
        Connection con = DbConnection.getInstance().getConnection();

        try {
            con.setAutoCommit(false);
            Order order = new Order(orderEdit.getOrderId(),orderEdit.getCustomerId(),orderEdit.getOrderDate(),orderEdit.getTime(),orderEdit.getTotal(),orderEdit.getDiscount());
            if(orderDAO.update(order)){
                System.out.println("Order updated");
                if (orderDetailDAO.deleteOrderDetail(orderEdit.getOrderId(),temp.getItemCode())) {
                    System.out.println("Order detail deleted");
                    //qtyOnHand update
                    Item item = itemDAO.search(temp.getItemCode());
                    item.setQtyOnHand(item.getQtyOnHand()+temp.getOrderQty());
                    if (itemDAO.update(item)) {
                        con.commit();
                        return true;
                    }else {
                        con.rollback();
                        return false;
                    }
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateOrderForEdit(OrderItemTm temp, Order order, int newQtyOnHand) {
        /*Connection con = DbConnection.getInstance().getConnection();
        try {
            con.setAutoCommit(false);
            if (orderDAO.update(order)) {
                OrderDetail orderDetail = new OrderDetail(order.getOrderId(),temp.getItemCode(),temp.getOrderQty(),temp.getUPrice());
                if (orderDetailDAO.updateOrderDetail(orderDetail)){
                    Item item = itemDAO.search(orderDetail.getItemCode());
                    item.setQtyOnHand(newQtyOnHand);
                    if (itemDAO.update(item)) {
                        con.commit();
                    }else {
                        con.rollback();
                        new Alert(Alert.AlertType.ERROR,"Item not Updated").show();
                        return false;
                    }
                }else {
                    con.rollback();
                    new Alert(Alert.AlertType.ERROR,"Order Detail not Updated").show();
                    return false;
                }
            }else {
                con.rollback();
                new Alert(Alert.AlertType.ERROR,"Order not Updated").show();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/

        return true;


    }

    @Override
    public ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException {
        ArrayList<OrderDetailDTO> details = new ArrayList<>();
        /*for (OrderDetail detail : orderDetailDAO.getOrderDetails(orderId)) {
            details.add(new OrderDetailDTO(detail.getOrderId(),detail.getItemCode(),detail.getOrderQty(),detail.getPrice()));
        }*/
        return details;
    }

    @Override
    public boolean cancelOrder(ArrayList<OrderDetailDTO> orderItemDetailList) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        String oId = "";
        for (OrderDetailDTO orderDetail : orderItemDetailList) {
            oId=orderDetail.getOrderId();
            System.out.println("OrderDetail :"+orderDetail);
            Item item = itemDAO.search(orderDetail.getItemCode());
            System.out.println("itemCode : "+orderDetail.getItemCode());
            System.out.println("@cancelOrder : item "+item);
            item.setQtyOnHand(item.getQtyOnHand() + orderDetail.getOrderQty());

            boolean isUpdatedItem = itemDAO.update(item);

            if (!isUpdatedItem) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }
        }

        boolean isDeleted = orderDAO.delete(oId);
        if (!isDeleted) {
            con.rollback();
            con.setAutoCommit(true);
            return false;
        }
        con.commit();
        con.setAutoCommit(true);
        return true;
    }

}



