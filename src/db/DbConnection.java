package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Wholesale Distributors",
                    "root",
                    "1234"
            );
            System.out.println("driver loaded successfully...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("loading failed..");
        }
    }

    public static DbConnection getInstance(){
        if(dbConnection==null){
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
