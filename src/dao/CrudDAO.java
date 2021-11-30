package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO{
     ArrayList<T> getAll() throws SQLException;

     T search(ID id) throws SQLException;

     boolean save(T t) throws SQLException;

     boolean update(T t) throws SQLException;

     boolean delete(ID id) throws SQLException;
}
