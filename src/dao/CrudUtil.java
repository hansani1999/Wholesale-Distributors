package dao;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static PreparedStatement getPreparedStatement(String sql, Object...args) throws SQLException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pst.setObject(i+1,args[i]);
        }
        return pst;
    }

    public static ResultSet executeQuery(String sql, Object...args) throws SQLException {
       return getPreparedStatement(sql, args).executeQuery();
    }

    public static boolean executeUpdate(String sql, Object...args) throws SQLException {
        return getPreparedStatement(sql, args).executeUpdate()>0;
    }
}
