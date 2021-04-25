package database;

import javafx.util.Pair;
import model.*;
import security.PasswordEncoder;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Commands {

  private final Controller controller;
  private final CommandsHelper helper;

  public Commands() {
    controller = new Controller();
    helper = new CommandsHelper(this);
  }

  //Users
  public List<User> listUsers() {
    try {
      return helper.makeUserList(controller.findAllUsers());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public User findUserById(long id) {
    try {
      ResultSet resultSet = controller.findUserById(id);
      if (resultSet.next()) {
        return helper.constructUserFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addUser(User user) {
    addUser(user.getUsername(), user.getPassword());
  }

  public void addUser(String username, String password) {
    try {
      password = PasswordEncoder.encode(password);
      if (controller.checkIfUserExistsInTable(username)) {
        System.err.println("user already exists");
      } else if (controller.addUserToTable(username, password)) {
        System.err.println("error");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void deleteUser(long id) {
    try {
      controller.deleteUserFromTableById(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //Clients

  public List<Client> listClients() {
    try {
      return helper.makeClientList(controller.findAllClients());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Client findClientById(long id) {
    try {
      ResultSet resultSet = controller.findClientById(id);
      if (resultSet.next()) {
        return helper.constructClientFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addClient(String name, String address, String phone, long userId) {
    try {
      if (controller.checkIfClientExistsInTable(phone)) {
        System.err.println("client already exists");
      } else if (controller.addClientToTable(name, address, phone, userId)) {
        System.err.println("error");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void addStaff(String firstName, String lastName, int salary, long departmentId, String role, long userId) {
    try {
      if (controller.addStaffToTable(firstName, lastName, salary, departmentId, role, userId)) {
        System.err.println("error");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Staff> listStaff() {
    try {
      return helper.makeStaffList(controller.listAllStaff());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addDepartment(String name) {
    try {
      if (controller.addDepartmentToTable(name)) {
        System.err.println("error");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Department> listDepartments() {
    try {
      return helper.makeDepartmentsList(controller.listAllDepartments());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addPosition(String name, int price, int realPrice) {
    try {
      if (controller.addPositionToTable(name, price, realPrice)) {
        System.err.println("error");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Position> listMenu() {
    try {
      return helper.makePositionsList(controller.listAllMenu());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void deletePosition(long id) {
    try {
      controller.deletePositionFromTableById(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void addOrder(Date date, boolean isProcessed, long clientId, Long staffId,
                       Map<Integer, Integer> positionIdNumberMap) {
    try {
      controller.addOrderToTable(date, isProcessed, clientId, staffId, positionIdNumberMap);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteOrder(long id) {
    try {
      controller.deleteOrderFromTableById(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Order> listOrders() {
    try {
      return helper.makeOrdersList(controller.listAllOrders());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Map<Position, Integer> findPositionsByOrderId(Long id) {
    try {
      var map = helper.makePositionIdNumberMap(controller.mapOrderPositionsByOrderId(id));
      return map.entrySet().stream().collect(Collectors.toMap(e -> findPositionById(e.getKey()), Map.Entry::getValue));
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Position findPositionById(Long id) {
    try {
      ResultSet resultSet = controller.findPositionById(id);
      if (resultSet.next()) {
        return helper.constructPositionFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }


  public List<Order> listOrdersByClientId(long clientId) {
    try {
      return helper.makeOrdersList(controller.listOrdersByClientId(clientId));
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addReview(int rate, String text, long clientId, long orderId) {
    try {
      controller.addReviewToTable(rate, text, clientId, orderId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Review> listReviews() {
    try {
      return helper.makeReviewsList(controller.listAllReviews());
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Client findClientByUserId(long userId) {
    try {
      ResultSet resultSet = controller.findClientByUserId(userId);
      if (resultSet.next()) {
        return helper.constructClientFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Staff findStaffByUserId(long userId) {
    try {
      ResultSet resultSet = controller.findStaffByUserId(userId);
      if (resultSet.next()) {
        return helper.constructStaffFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public User findUserByUsername(String username) {
    try {
      ResultSet resultSet = controller.findUserByUsername(username);
      if (resultSet.next()) {
        return helper.constructUserFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void deleteDepartment(long id) {
    try {
      controller.deleteDepartmentFromTableById(id);
    } catch (SQLException e) {
      System.err.println("You can not delete department until it contains staff");
    }
  }

  public Pair<Integer, Position> findMostPopularPosition() {
    try {
      ResultSet resultSet = controller.findMostPopularPosition();
      if (resultSet.next()) {
        int number = resultSet.getInt("pos_number");
        Position pos = helper.constructPositionFromResultSet(resultSet);
        return new Pair<>(number, pos);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Integer calculateMonthlyIncome() {
    try {
      ResultSet resultSet = controller.calculateMonthlyIncome();
      if (resultSet.next()) {
        return resultSet.getInt("income");
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Staff findMostPayedStaff() {
    try {
      ResultSet resultSet = controller.findMostPayedStaff();
      if (resultSet.next()) {
        return helper.constructStaffFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Staff findLessPayedStaff() {
    try {
      ResultSet resultSet = controller.findLessPayedStaff();
      if (resultSet.next()) {
        return helper.constructStaffFromResultSet(resultSet);
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void updateClient(long id, String name, String address, String phone) {
    try {
      controller.updateClientById(id, name, address, phone);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updatePosition(long id, String name, int price, int realPrice) {
    try {
      controller.updatePositionById(id, name, price, realPrice);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
