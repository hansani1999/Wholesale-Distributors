package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Order;
import views.tm.OrderItemTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    ArrayList<CustomDTO> getOrderItemDetails(String oId) throws SQLException;

    OrderDTO searchOrder(String oId) throws SQLException;

    boolean deleteOrder(String oId) throws SQLException;

    public boolean updateOrderForDelete(CustomDTO temp, OrderDTO orderEdit);

    ArrayList<ItemDTO> getAllItems() throws SQLException;

    ArrayList<OrderDTO> getCustomerOrder(String text) throws SQLException;

    ItemDTO searchItem(String itemCode) throws SQLException;

    OrderDetailDTO searchOrderDetail(String oId, String itemCode) throws SQLException;

    public boolean updateOrderForEdit(OrderItemTm temp, Order order, int newQtyOnHand);

    ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException;

    boolean cancelOrder(ArrayList<OrderDetailDTO> orderItemDetailList) throws SQLException;
}
