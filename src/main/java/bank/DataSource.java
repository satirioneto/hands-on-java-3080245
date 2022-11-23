package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Class call [DataSource]
public class DataSource {
  // Method call [connect]
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection =  null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected");
    
    } catch(SQLException e){
      e.printStackTrace();
    }

    return connection;
  }
  // Based in the CLASS [Customer] this method create a customer finding in database.
  public static Customer getCustomer(String username){
    String sql = "select * from customers where username = ?";
    Customer customer = null;

    try(Connection connection = connect();
        PreparedStatement statement =  connection.prepareStatement(sql)){

          statement.setString(1, username);
          try(ResultSet resultSet = statement.executeQuery()){
            customer = new Customer(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getString("username"),
              resultSet.getString("password"),
              resultSet.getInt("account_id"));
          }

    } catch(SQLException e){
      e.printStackTrace();
    }
    return customer;
  }

  public static void main(String[] args){
    Customer customer = getCustomer("twest8o@friendfeed.com");
    System.out.println(customer.getName());
  }
}
