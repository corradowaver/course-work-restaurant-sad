package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

  private static final String URL = "jdbc:sqlserver://127.0.0.1:1433;database=restaurant;";
  private static final String USER = "cringe";
  private static final String PASSWORD = "cringe";

  public static Connection connectDB() {
    try {
      return DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException("Could not connect db");
    }
  }

}
