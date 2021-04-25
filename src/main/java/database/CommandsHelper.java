package database;

import database.Commands;
import model.*;
import database.*;

import java.security.Security;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class CommandsHelper {
  Commands commands;

  public CommandsHelper(Commands commands) {
    this.commands = commands;
  }

  public List<Client> makeClientList(ResultSet resultSet) throws SQLException {
    List<Client> clients = new ArrayList<>();
    while (resultSet.next()) {
      clients.add(constructClientFromResultSet(resultSet));
    }
    return clients;
  }

  public User constructUserFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String username = resultSet.getString("username");
    String password = resultSet.getString("password");
    return new User(id, username, password);
  }

  public List<User> makeUserList(ResultSet resultSet) throws SQLException {
    List<User> users = new ArrayList<>();
    while (resultSet.next()) {
      users.add(constructUserFromResultSet(resultSet));
    }
    return users;
  }

  public Client constructClientFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String name = resultSet.getString("name");
    String address = resultSet.getString("address");
    String phone = resultSet.getString("phone");
    User user = commands.findUserById(resultSet.getLong("account_id"));
    return new Client(id, name, address, phone, user);
  }

  public List<Staff> makeStaffList(ResultSet resultSet) throws SQLException {
    List<Staff> staffList = new ArrayList<>();
    while (resultSet.next()) {
      staffList.add(constructStaffFromResultSet(resultSet));
    }
    return staffList;
  }

  public Staff constructStaffFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String firstName = resultSet.getString("first_name");
    String lastName = resultSet.getString("last_name");
    int salary = resultSet.getInt("salary");
    long departmentId = resultSet.getLong("department_id");
    String role = resultSet.getString("role");
    User user = commands.findUserById(resultSet.getLong("account_id"));
    return new Staff(id, firstName, lastName, salary, departmentId, role, user);
  }

  public List<Department> makeDepartmentsList(ResultSet resultSet) throws SQLException {
    List<Department> departments = new ArrayList<>();
    while (resultSet.next()) {
      departments.add(constructDepartmentFromResultSet(resultSet));
    }
    return departments;
  }

  public Department constructDepartmentFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String name = resultSet.getString("name");
    return new Department(id, name);
  }

  public List<Position> makePositionsList(ResultSet resultSet) throws SQLException {
    List<Position> menu = new ArrayList<>();
    while (resultSet.next()) {
      menu.add(constructPositionFromResultSet(resultSet));
    }
    return menu;
  }

  public Position constructPositionFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String name = resultSet.getString("name");
    int price = resultSet.getInt("price");
    int realPrice = resultSet.getInt("real_price");
    return new Position(id, name, price, realPrice);
  }

  public List<Order> makeOrdersList(ResultSet resultSet) throws SQLException {
    List<Order> orders = new ArrayList<>();
    while (resultSet.next()) {
      orders.add(constructOrderFromResultSet(resultSet));
    }
    return orders;
  }

  public Order constructOrderFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    Date date = resultSet.getDate("date");
    boolean isProcessed = resultSet.getBoolean("is_processed");
    long clientId = resultSet.getLong("client_id");
    long staffId = resultSet.getLong("staff_id");
    return new Order(id, date, isProcessed, clientId, staffId);
  }

  public List<Review> makeReviewsList(ResultSet resultSet) throws SQLException {
    List<Review> reviews = new ArrayList<>();
    while (resultSet.next()) {
      reviews.add(constructReviewFromResultSet(resultSet));
    }
    return reviews;
  }

  public Review constructReviewFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    int rate = resultSet.getInt("rate");
    String text = resultSet.getString("text");
    long clientId = resultSet.getLong("client_id");
    long orderId = resultSet.getLong("order_id");
    return new Review(id, rate, text, clientId, orderId);
  }

  public Map<Long, Integer> makePositionIdNumberMap(ResultSet resultSet) throws SQLException {
    Map<Long, Integer> map = new HashMap<>();
    while (resultSet.next()) {
      var entry = constructPositionIdNumberFromResultSet(resultSet);
      map.putIfAbsent(entry.getKey(), entry.getValue());
    }
    return map;
  }

  public Map.Entry<Long, Integer> constructPositionIdNumberFromResultSet(ResultSet resultSet) throws SQLException {
    long positionId = resultSet.getLong("position_id");
    int positionsNumber = resultSet.getInt("positions_number");
    return Map.entry(positionId, positionsNumber);
  }
}
