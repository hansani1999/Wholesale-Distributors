package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DashboardBO extends SuperBO {
    ArrayList<CustomDTO> getItemMobility() throws SQLException;
    public int getCustomerCount(String date) throws SQLException;
    public double getTodayIncome(String date) throws SQLException;
    public double getTotalIncome() throws SQLException;
}
