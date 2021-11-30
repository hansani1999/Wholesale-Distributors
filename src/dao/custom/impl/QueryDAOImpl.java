package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import db.DbConnection;
import dto.CustomDTO;
import dto.OrderDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomDTO> getOrderItemDetails(String oId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT od.itemCode,i.description,od.OrderQTy,od.price FROM `Order` o INNER JOIN `Order Detail` od  ON o.orderId=od.orderId INNER JOIN Item i ON od.itemCode=i.code WHERE o.orderId=?", oId);
        ArrayList<CustomDTO> itemList = new ArrayList<>();
        while (rst.next()){
            itemList.add(new CustomDTO(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getDouble(4)));
        }
        return itemList;

    }
}
