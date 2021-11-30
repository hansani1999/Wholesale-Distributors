package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.impl.*;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    //private ItemDAO itemDAO = new ItemDAOImpl();
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    ItemDAOImpl impl = new ItemDAOImpl();

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : impl.getAllItems()) {
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
    public ItemDTO searchItem(String code) throws SQLException {
        Item item = impl.searchItem(code);
        return new ItemDTO(item.getCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice());
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException {
        return impl.saveItem(new Item(dto.getCode(),dto.getDescription(),dto.getPackSize(),dto.getQtyOnHand(),dto.getUnitPrice()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException {
        return impl.updateItem(new Item(dto.getCode(),dto.getDescription(),dto.getPackSize(),dto.getQtyOnHand(),dto.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException {
        return impl.deleteItem(code);
    }

    @Override
    public boolean ifExistsItem(String code) throws SQLException {
        return impl.isExists(code);
    }
}
