package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, properties);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("db.properties"));
            return properties;

        } catch (IOException e) {
            throw new DBException(e.getMessage());
        }
    }

    public static void closeConnection(){
        if(connection != null){
            try {
                connection.close();

            }catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement s){
        try{
            s.close();
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet rs){
        try{
            rs.close();
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    public static void gravarCandidato(ResultSet rs){

    }
}
