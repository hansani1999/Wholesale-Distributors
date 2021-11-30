package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException;

    ItemDTO searchItem(String text) throws SQLException;

    boolean saveItem(ItemDTO i) throws SQLException;

    boolean updateItem(ItemDTO i) throws SQLException;

    boolean deleteItem(String text) throws SQLException;

    public boolean ifExistsItem(String code) throws SQLException;
}
