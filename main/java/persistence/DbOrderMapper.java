package persistence;
import domain.Orders;

import java.sql.*;

public class DbOrderMapper {

    private Database database;

    public DbOrderMapper(Database database) {
        this.database = database;
    }

    public boolean newOrder (Orders order){
        boolean result = false;
        String sql = "insert into orders (pizza_no,amount,pickup_time,customer_name,phone) values (?,?,?,?,?)";
        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getPizzaNo());
                ps.setInt(2, order.getAmount());
                ps.setInt(3, order.getPickupTime());
                ps.setString(4, order.getCustomerName());
                ps.setString(5, order.getPhoneNo());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected >= 1){
                    result = true;
                }

            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            // TODO: Make own throwable exception and let it bubble upwards
            throwables.printStackTrace();
        }       return result;


    }

}
