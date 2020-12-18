package persistence;

import domain.CustomExceptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;
    private final String USER;
    private final String PASSWORD;
    private final String URL;

    public Database(String user, String password, String url) throws CustomExceptions {
        USER = user;
        PASSWORD = password;
        URL = url;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
           throw new CustomExceptions("Fejl ved instantiering af Driverklassen");
        }
    }

    public Connection connect() throws CustomExceptions {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
           throw new CustomExceptions("Fejl under etablering af forbindelse til database");

        }
        return connection;
    }

}
