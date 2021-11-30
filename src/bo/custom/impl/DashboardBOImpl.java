package bo.custom.impl;

import bo.custom.DashboardBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dto.CustomDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardBOImpl implements DashboardBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    //CustomerDAO customerDAO = (CustomerDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO)DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    ItemDAOImpl impl = new ItemDAOImpl();

    @Override
    public ArrayList<CustomDTO> getItemMobility() throws SQLException {
        ArrayList<CustomDTO> list = itemDAO.getItemExpenditures();
        return list;
    }

    @Override
    public int getCustomerCount(String date) throws SQLException {
       return customerDAO.getCustomerCount(date);
    }

    @Override
    public double getTodayIncome(String date) throws SQLException {
        return orderDAO.getTodayIncome(date);
    }

    @Override
    public double getTotalIncome() throws SQLException {
        return orderDAO.getTotalIncome();
    }
}
