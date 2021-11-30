package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import dao.custom.impl.*;
import db.DbConnection;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import dto.ItemDTO;
import dto.ItemDetails;
import dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private OrderDAO orderDAO = (OrderDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    //private CustomerDAO customerDAO = (CustomerDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private OrderDetailDAO orderDetailDAO = (OrderDetailDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    private QueryDAO queryDAO = (QueryDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);
    CustomerDAOImpl implCust = new CustomerDAOImpl();
    ItemDAOImpl implItem = new ItemDAOImpl();


    @Override
    public ArrayList<dto.CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<dto.CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer c : implCust.getAllCustomers()) {
            allCustomers.add(new dto.CustomerDTO(
                    c.getId(),
                    c.getTitle(),
                    c.getName(),
                    c.getAddress(),
                    c.getCity(),
                    c.getProvince(),
                    c.getPostalCode()
            ));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : implItem.getAllItems()) {
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
    public ItemDTO searchItem(String itemCode) throws SQLException {
        Item item = implItem.searchItem(itemCode);
        return new ItemDTO(item.getCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice());
    }

    @Override
    public dto.CustomerDTO searchCustomer(String customerId) throws SQLException {
        Customer customer = implCust.searchCustomer(customerId);
        return new dto.CustomerDTO(
                customer.getId(),
                customer.getTitle(),
                customer.getName(),
                customer.getAddress(),
                customer.getAddress(),
                customer.getCity(),
                customer.getPostalCode());
    }

    @Override
    public boolean placeOrder(OrderDTO dto) throws SQLException {
            Order order = new Order(dto.getOrderId(),
                    dto.getCustomerId(),
                    dto.getOrderDate(),
                    dto.getTime(),
                    dto.getTotal(),
                    dto.getDiscount()
            );
            Connection con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isSavedOrder = orderDAO.save(order);
            if (!isSavedOrder) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

            ArrayList<ItemDetails> items = dto.getItems();
            for (ItemDetails details : items) {
                OrderDetail detail = new OrderDetail(dto.getOrderId(),details.getItemCode(),details.getQtyForSell(),details.getUnitPrice());
                boolean isSavedDetail = orderDetailDAO.save(detail);

                if (!isSavedDetail) {
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }

                Item item = implItem.search(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand()-detail.getOrderQty());
                boolean isUpdatedQty = implItem.update(item);
                if (!isUpdatedQty) {
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
            }

            con.commit();
            con.setAutoCommit(true);
            return true;

        }

    @Override
    public String generateOrderId() throws SQLException {
        return orderDAO.generateOrderId();
    }

}
