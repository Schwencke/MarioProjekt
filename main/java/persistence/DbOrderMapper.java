package persistence;
import domain.*;
import domain.Orders;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOrderMapper {

    private Database database;

    public DbOrderMapper(Database database) {
        this.database = database;
    }

    public boolean newOrder (Orders order) throws CustomExceptions {
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

            } catch (SQLException e) {
                throw new CustomExceptions("SQL FEJL");
            }
        } catch (SQLException throwables) {
            // TODO: Make own throwable exception and let it bubble upwards
            throwables.printStackTrace();
        }       return result;


    }
    public List<Orders> readOrders() throws CustomExceptions {

        List<Orders> orders = new ArrayList<>();

        String sql = "select * from orders Order by pickup_time ASC";

        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getBoolean("removed") ==false){
                    int orderId = rs.getInt("order_id");
                    int pizzaNo = rs.getInt("pizza_no");
                    int amount = rs.getInt("amount");
                    int pickUpTime = rs.getInt("pickup_time");
                    Timestamp orderTime = rs.getTimestamp("order_time");
                    String phoneNo = rs.getString("phone");
                    String customerName = rs.getString("customer_name");
                    boolean removed = rs.getBoolean("removed");
                    orders.add(new Orders(orderId, pizzaNo,amount,pickUpTime,orderTime,customerName,phoneNo,removed));
                }}
            } catch (SQLException e) {
                throw new CustomExceptions("SQL FEJL");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public Orders getOrderById(int ordreId) throws CustomExceptions {
        Orders orders = null;
        String sql = "select * from orders where order_id = ?";
        try (Connection connection = database.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, ordreId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    int pizzaNo = rs.getInt("pizza_no");
                    int amount = rs.getInt("amount");
                    String customerName = rs.getString("customer_name");
                    int pickupTime = rs.getInt("pickup_time");
                    String phone = rs.getString("phone");
                    orders = new Orders(orderId, pizzaNo, amount,pickupTime, customerName, phone);
                }
            } catch (SQLException e) {
                throw new CustomExceptions("SQL FEJL");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrders(Orders orders) throws CustomExceptions {
        boolean result = false;
        String sql = "update orders set pizza_no = ?, amount = ?, pickup_time = ?, customer_name = ?, phone = ? where order_id = ?";
      try {
          try (Connection connection = database.connect()) {
              try (PreparedStatement ps = connection.prepareStatement(sql)) {
                  ps.setInt(1, orders.getPizzaNo());
                  ps.setInt(2, orders.getAmount());
                  ps.setInt(3, orders.getPickupTime());
                  ps.setString(4, orders.getCustomerName());
                  ps.setString(5, orders.getPhoneNo());
                  ps.setInt(6, orders.getOrderID());
                  int rowsAffected = ps.executeUpdate();
                  if (rowsAffected >= 1) {
                      result = true;
                  }
              } catch (SQLException e) {
                  throw new CustomExceptions("SQL FEJL");
              }}
          } catch (SQLException throwables) {
              // TODO: Make own throwable exception and let it bubble upwards
              throwables.printStackTrace();
          }
          return result;

    }

    public List<Statistics> statisticsArchived() throws CustomExceptions {

        List<Statistics> statisticsList = new ArrayList<>();
        String sql = "select * from orders where removed = ?";

            try (Connection connection = database.connect();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setBoolean(1, true);
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        int orderID = resultSet.getInt("order_id");
                        int pizzaNo = resultSet.getInt("pizza_no");
                        int amount = resultSet.getInt("amount");
                        int pickupTime = resultSet.getInt("pickup_time");
                        Date orderTime = resultSet.getDate("order_time");
                        String phone = resultSet.getString("phone");
                        statisticsList.add(new Statistics(orderID, pizzaNo, amount, pickupTime, orderTime, phone));
                    }
                } catch (SQLException e) {
                throw new CustomExceptions("SQL FEJL");
                }
            return statisticsList;
        }



        public boolean removeOrder(int orderId) throws CustomExceptions {

        boolean result = false;
        String sql = "update orders set removed = 1 where order_id = ? ";

       try {
           try (Connection connection = database.connect()) {

               try (PreparedStatement ps = connection.prepareStatement(sql)) {

                   ps.setInt(1, orderId);
                   int rowsaffected = ps.executeUpdate();
                   if (rowsaffected >= 1) {
                       result = true;
                   }
               }
           }
       } catch (SQLException e) {
           throw new CustomExceptions("SQL FEJL");
       }
            return result;
        }}







