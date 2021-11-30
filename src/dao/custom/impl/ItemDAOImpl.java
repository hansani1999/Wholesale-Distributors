package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import dto.CustomDTO;
import entity.Customer;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT  * FROM Item");
        ArrayList<Item> ids = new ArrayList<>();
        while(rst.next()){
            ids.add(new Item(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5))
            );
        }
        return ids;
    }

    @Override
    public Item search(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE code=?",id);
        if(rst.next()){
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
        }
        else {
            return null;
        }
    }

    @Override
    public boolean save(Item item) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?)",item.getCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice());
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, qtyOnHand=?, unitPrice=? WHERE code=?",item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice(),item.getCode());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return  CrudUtil.executeUpdate("DELETE FROM Item WHERE code=?",id);
    }

    @Override
    public ArrayList<CustomDTO> getItemExpenditures() throws SQLException {
        ArrayList<CustomDTO> list = new ArrayList();
        ResultSet rst =   CrudUtil.executeQuery("SELECT itemCode,SUM(OrderQTy) from `Order Detail` GROUP BY itemCode");

        while (rst.next()){
            list.add(new CustomDTO(
                rst.getString(1),
                rst.getInt(2)
            ));
        }
        return list;
    }

    @Override
    public boolean isExistsItem(String code) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);

        if (rst.next()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean saveItem(Item item){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(item);

        transaction.commit();
        session.close();

        return true;
    }

    public boolean updateItem(Item item){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(item);

        transaction.commit();
        session.close();

        return true;
    }

    public boolean deleteItem(String code){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class,code);
        session.delete(item);

        transaction.commit();
        session.close();

        return true;
    }

    public List<Item> getAllItems(){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Item ";
        Query query = session.createQuery(hql);
        List<Item> allCustomers = query.list();

        transaction.commit();
        session.close();

        return allCustomers;
    }


    public Item searchItem(String code){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class,code);

        transaction.commit();
        session.close();

        return item;
    }

    public boolean isExists(String id){
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        String hql = "FROM Item WHERE id = :item_code";
        Query query = session.createQuery(hql);
        query.setParameter("item_code",id);
        List<Item> itemList= query.list();

        boolean ifExists =false;
        for (Item item : itemList) {
            if (id.equals(item.getCode())) {
                ifExists=true;
            }
        }

        transaction.commit();
        session.close();

        return ifExists;
    }


}
