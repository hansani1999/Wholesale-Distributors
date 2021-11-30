package dao.custom;

import dao.SuperDAO;
import dto.CustomDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public ArrayList<CustomDTO> getOrderItemDetails(String oId) throws SQLException;
}
