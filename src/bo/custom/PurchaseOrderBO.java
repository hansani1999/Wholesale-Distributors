package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException;

    ArrayList<ItemDTO> getAllItems() throws SQLException;

    ItemDTO searchItem(String itemCode) throws SQLException;

    CustomerDTO searchCustomer(String customerId) throws SQLException;

    /*boolean placeOrder(OrderDTO order) throws SQLException;*/

    boolean placeOrder(OrderDTO dto, String cId) throws SQLException;

    String generateOrderId() throws SQLException;
}
