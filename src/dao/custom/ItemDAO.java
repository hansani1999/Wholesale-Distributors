package dao.custom;

import dao.CrudDAO;
import dto.CustomDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    public ArrayList<CustomDTO> getItemExpenditures() throws SQLException;
    public boolean isExistsItem(String code) throws SQLException;
}
