package query.templates;

public class QueryTemplates {
  public final static String LIST_ALL_CLIENTS = "SELECT * FROM Clients";
  public final static String LIST_ALL_USERS = "SELECT * FROM Users";
  public final static String SEARCH_USER_BY_ID = "SELECT * FROM Users WHERE id = ?";
  public final static String SEARCH_USER_BY_USERNAME = "SELECT * FROM Users WHERE username = ?";
  public final static String ADD_USER = "INSERT INTO Users (username, password) VALUES (?, ?)";
  public final static String DELETE_USER_BY_ID = "DELETE FROM Users WHERE id = ?";
  public final static String SEARCH_CLIENT_BY_ID = "SELECT * FROM Clients WHERE id = ?";
  public final static String SEARCH_CLIENT_BY_PHONE = "SELECT * FROM Clients WHERE phone = ?";
  public final static String ADD_CLIENT = "INSERT INTO Clients (name, address, phone, account_id) VALUES (?, ?, ?, ?)";
  public final static String LIST_ALL_STAFF = "SELECT * FROM Staff";
  public final static String ADD_STAFF = "INSERT INTO Staff (first_name, last_name, salary, department_id, account_id, role) VALUES (?, ?, ?, ?, ?, ?)";
  public final static String ADD_DEPARTMENT = "INSERT INTO Departments (name) VALUES (?)";
  public final static String LIST_ALL_DEPARTMENTS = "SELECT * FROM Departments";
  public final static String ADD_POSITION = "INSERT INTO Menu (name, price, real_price) VALUES (?, ?, ?)";
  public final static String LIST_ALL_MENU = "SELECT * FROM show_menu";
  public final static String DELETE_POSITION_BY_ID = "DELETE FROM Menu WHERE id = ?";
  public static final String ADD_ORDER = "INSERT INTO Orders (date, is_processed, client_id, staff_id) VALUES (?, ?, ?, ?)";
  public static final String ADD_ORDER_POSITION = "INSERT INTO Orders_Menu (order_id, position_id, positions_number) VALUES (?, ?, ?)";
  public static final String LIST_ALL_ORDERS = "SELECT * FROM Orders";
  public static final String ADD_REVIEW = "INSERT INTO Reviews (rate, text, client_id, order_id) VALUES (?, ?, ?, ?)";
  public static final String LIST_ALL_REVIEWS = "SELECT * FROM Reviews";

  public static final String DELETE_ORDER_BY_ID = "DELETE FROM Orders WHERE id = ?";
  public static final String SEARCH_CLIENT_BY_USER_ID = "SELECT * FROM Clients WHERE account_id = ?";
  public static final String SEARCH_STAFF_BY_USER_ID = "SELECT * FROM Staff WHERE account_id = ?";
  public static final String SEARCH_ORDERS_BY_CLIENT_ID = "SELECT * FROM Orders WHERE client_id = ?";
  public static final String SEARCH_POSITION_BY_ID = "SELECT * FROM Menu WHERE id = ?";
  public static final String SEARCH_ORDER_POSITION_BY_ORDER_ID = "SELECT * FROM Orders_Menu WHERE order_id = ?";
  public static final String DELETE_DEPARTMENT_BY_ID = "DELETE FROM Departments WHERE id = ?";
  public static final String SEARCH_MOST_POPULAR_POSITION = "SELECT pos_number, id, name, price, real_price FROM  (\n" +
      "\tSELECT\n" +
      "\t\tTOP 1\n" +
      "\t\tSUM(positions_number) AS pos_number, \n" +
      "\t\tposition_id AS posId\n" +
      "\tFROM Orders_Menu\n" +
      "\tGROUP BY position_id\n" +
      "\tORDER BY pos_number DESC\n" +
      ") AS a\n" +
      "JOIN Menu ON a.posId = Menu.id ";
  public static final String CALCULATE_MONTHLY_INCOME = "SELECT \n" +
      "\tSUM(price) - SUM(real_price) as income FROM (\n" +
      "\t\tSELECT * FROM Orders\n" +
      "\t\tWHERE \n" +
      "\t\t\tdate >= DATEADD(MONTH, -1, GETDATE()) \n" +
      "\t\t) as processedOrders\n" +
      "\tJOIN Orders_Menu ON processedOrders.id = Orders_Menu.order_id\n" +
      "\tJOIN Menu ON position_id = Menu.id";
  public static final String SEARCH_MOST_PAYED_STAFF = "SELECT * FROM show_highest_paid";
  public static final String SEARCH_LESS_PAYED_STAFF = "SELECT * FROM show_most_underpaid";
  public static final String UPDATE_CLIENT = "UPDATE clients SET name = ?," +
      "address = ?, " +
      "phone = ? " +
      "WHERE id = ?";
  public static final String UPDATE_POSITION = "UPDATE menu SET name = ?," +
      "price = ?, " +
      "real_price = ? " +
      "WHERE id = ?";;
  ;
}
