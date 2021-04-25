package database;

import query.templates.QueryTemplates;

import java.sql.*;
import java.util.Map;

public class Controller {
  private static Connection dbConnection;

  public Controller() {
    dbConnection = Connector.connectDB();
  }

  public ResultSet findAllClients() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_CLIENTS);
  }

  public ResultSet findUserById(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_USER_BY_ID);
    preparedStatement.setLong(1, id);
    return preparedStatement.executeQuery();
  }

  public ResultSet findAllUsers() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_USERS);
  }

  public boolean checkIfUserExistsInTable(String username) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_USER_BY_USERNAME);
    preparedStatement.setString(1, username);
    return preparedStatement.executeQuery().next();
  }

  public boolean addUserToTable(String username, String password) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_USER);
    preparedStatement.setString(1, username);
    preparedStatement.setString(2, password);
    return preparedStatement.execute();
  }

  public boolean deleteUserFromTableById(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.DELETE_USER_BY_ID);
    preparedStatement.setLong(1, id);
    return preparedStatement.execute();
  }

  public ResultSet findClientById(long id) throws  SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_CLIENT_BY_ID);
    preparedStatement.setLong(1, id);
    return preparedStatement.executeQuery();
  }

  public boolean checkIfClientExistsInTable(String phone) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_CLIENT_BY_PHONE);
    preparedStatement.setString(1, phone);
    return preparedStatement.executeQuery().next();
  }

  public boolean addClientToTable(
      String name,
      String address,
      String phone,
      long userId
  ) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_CLIENT);
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, address);
    preparedStatement.setString(3, phone);
    preparedStatement.setLong(4, userId);
    return preparedStatement.execute();
  }

  public ResultSet listAllStaff() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_STAFF);
  }

  public boolean addStaffToTable(String firstName, String lastName, int salary, long departmentId, String role, long userId)
      throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_STAFF);
    preparedStatement.setString(1, firstName);
    preparedStatement.setString(2, lastName);
    preparedStatement.setInt(3, salary);
    preparedStatement.setLong(4, departmentId);
    preparedStatement.setLong(5, userId);
    preparedStatement.setString(6, role);
    return preparedStatement.execute();
  }

  public boolean addDepartmentToTable(String name) throws SQLException {
      PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_DEPARTMENT);
      preparedStatement.setString(1, name);
      return preparedStatement.execute();
  }

  public ResultSet listAllDepartments() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_DEPARTMENTS);
  }

  public boolean addPositionToTable(String name, int price, int realPrice) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_POSITION);
    preparedStatement.setString(1, name);
    preparedStatement.setInt(2, price);
    preparedStatement.setInt(3, realPrice);
    return preparedStatement.execute();
  }

  public ResultSet listAllMenu() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_MENU);
  }

  public boolean deletePositionFromTableById(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.DELETE_POSITION_BY_ID);
    preparedStatement.setLong(1, id);
    return preparedStatement.execute();
  }

  public void addOrderToTable(Date date, boolean isProcessed, long clientId, Long staffId,
                                 Map<Integer, Integer> positionIdNumberMap) throws SQLException {
    //add order
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
    preparedStatement.setDate(1, date);
    preparedStatement.setBoolean(2, isProcessed);
    preparedStatement.setLong(3, clientId);
    if (staffId != null) {
      preparedStatement.setLong(4, staffId);
    } else {
      preparedStatement.setNull(4, java.sql.Types.NULL);
    }
    int affectedRows = preparedStatement.executeUpdate();
    if (affectedRows == 0) {
      throw new SQLException("Creating order failed, no rows affected.");
    }
    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
      long orderId;
      if (generatedKeys.next()) {
        orderId = generatedKeys.getLong(1);
        PreparedStatement ps = dbConnection.prepareStatement(QueryTemplates.ADD_ORDER_POSITION);
        positionIdNumberMap.forEach((positionId, number) -> {
          try {
            ps.setLong(1, orderId);
            ps.setLong(2, positionId);
            ps.setInt(3, number);
            ps.execute();
          } catch (SQLException throwables) {
            throwables.printStackTrace();
          }
        });
      } else {
        throw new SQLException("Creating order failed, no ID obtained.");
      }
    }
  }

  public void deleteOrderFromTableById(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.DELETE_ORDER_BY_ID);
    preparedStatement.setLong(1, id);
    preparedStatement.executeUpdate();
  }

  public ResultSet listAllOrders() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_ORDERS);
  }

  public boolean addReviewToTable(int rate, String text, long clientId, long orderId) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.ADD_REVIEW);
    preparedStatement.setInt(1, rate);
    preparedStatement.setString(2, text);
    preparedStatement.setLong(3, clientId);
    preparedStatement.setLong(4, orderId);
    return preparedStatement.execute();
  }

  public ResultSet listAllReviews() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.LIST_ALL_REVIEWS);
  }

  public ResultSet findClientByUserId(long userId) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_CLIENT_BY_USER_ID);
    preparedStatement.setLong(1, userId);
    return preparedStatement.executeQuery();
  }

  public ResultSet findStaffByUserId(long userId) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_STAFF_BY_USER_ID);
    preparedStatement.setLong(1, userId);
    return preparedStatement.executeQuery();
  }

  public ResultSet findUserByUsername(String username) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_USER_BY_USERNAME);
    preparedStatement.setString(1, username);
    return preparedStatement.executeQuery();
  }

  public ResultSet listOrdersByClientId(long clientId) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_ORDERS_BY_CLIENT_ID);
    preparedStatement.setLong(1, clientId);
    return preparedStatement.executeQuery();
  }

  public ResultSet findPositionById(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_POSITION_BY_ID);
    preparedStatement.setLong(1, id);
    return preparedStatement.executeQuery();
  }

  public ResultSet mapOrderPositionsByOrderId(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.SEARCH_ORDER_POSITION_BY_ORDER_ID);
    preparedStatement.setLong(1, id);
    return preparedStatement.executeQuery();
  }

  public void deleteDepartmentFromTableById(long id) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.DELETE_DEPARTMENT_BY_ID);
    preparedStatement.setLong(1, id);
    preparedStatement.executeUpdate();
  }

  public ResultSet findMostPopularPosition() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.SEARCH_MOST_POPULAR_POSITION);
  }

  public ResultSet calculateMonthlyIncome() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.CALCULATE_MONTHLY_INCOME);
  }

  public ResultSet findMostPayedStaff() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.SEARCH_MOST_PAYED_STAFF);
  }

  public ResultSet findLessPayedStaff() throws SQLException {
    return dbConnection.createStatement().executeQuery(QueryTemplates.SEARCH_LESS_PAYED_STAFF);
  }

  public void updateClientById(long id, String name, String address, String phone) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.UPDATE_CLIENT);
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, address);
    preparedStatement.setString(3, phone);
    preparedStatement.setLong(4, id);
    preparedStatement.execute();
  }

  public void updatePositionById(long id, String name, int price, int realPrice) throws SQLException {
    PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryTemplates.UPDATE_POSITION);
    preparedStatement.setString(1, name);
    preparedStatement.setInt(2, price);
    preparedStatement.setInt(3, realPrice);
    preparedStatement.setLong(4, id);
    preparedStatement.execute();
  }
}
