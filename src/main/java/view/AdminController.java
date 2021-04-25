package view;

import database.Commands;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.Department;
import model.Position;
import model.Staff;
import model.User;

import java.util.HashSet;
import java.util.stream.Collectors;

public class AdminController {
  private MainApp mainApp;
  private Commands commands = new Commands();
  @FXML
  private TableView<Position> positionsTable;
  @FXML
  private TableColumn<Position, String> positionNameColumn;
  @FXML
  private TableColumn<Position, String> positionPriceColumn;
  @FXML
  private TableColumn<Position, String> positionRealPriceColumn;
  @FXML
  private Button addButton;
  @FXML
  private Button deleteButton;
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField priceTextField;
  @FXML
  private TextField realPriceTextField;
  @FXML
  private TableView<Staff> staffTable;
  @FXML
  private TableColumn<Staff, String> staffFirstNameColumn;
  @FXML
  private TableColumn<Staff, String> staffLastNameColumn;
  @FXML
  private TableColumn<Staff, String> staffSalaryColumn;
  @FXML
  private TableColumn<Staff, String> staffDepartmentColumn;
  @FXML
  private TableColumn<Staff, String> staffRoleColumn;
  @FXML
  private TextField staffUsernameField;
  @FXML
  private TextField staffPasswordField;
  @FXML
  private TextField staffFirstNameField;
  @FXML
  private TextField staffLastNameField;
  @FXML
  private TextField staffSalaryField;
  @FXML
  private ComboBox<Department> staffDepartmentComboBox;
  @FXML
  private TextField staffRoleField;
  @FXML
  private Button addStaffButton;
  @FXML
  private Button deleteStaffButton;
  @FXML
  private Button happyStaffButton;
  @FXML
  private Button sadStaffButton;
  @FXML
  private TableView<Department> departmentsTable;
  @FXML
  private TableColumn<Department, String> departmentsNameColumn;
  @FXML
  private Button addDepartmentButton;
  @FXML
  private Button deleteDepartmentButton;
  @FXML
  private Button monthlyIncomeButton;
  @FXML
  private Button mostPopularPositionButton;
  @FXML
  private TextField departmentNameField;
  @FXML
  private Button updateButton;

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;
  }

  @FXML
  private void initialize() {
    initTables();
    updateMenuTable();
    updateStaffTable();
    updateDepartmentsTable();
    updateDepartmentsComboBox();
  }

  private void initTables() {
    positionNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    positionPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
    positionRealPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRealPrice())));

    staffFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
    staffLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
    staffSalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSalary())));
    staffDepartmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getDepartmentByDepartmentId(cellData.getValue().getDepartmentId())));
    staffRoleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));

    departmentsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
  }

  private void updateMenuTable() {
    positionsTable.setItems(FXCollections.observableArrayList(commands.listMenu()));
  }

  private void updateStaffTable() {
    staffTable.setItems(FXCollections.observableArrayList(commands.listStaff()));
  }

  private void updateDepartmentsTable() {
    departmentsTable.setItems(FXCollections.observableArrayList(commands.listDepartments()));
  }

  @FXML
  private void updateDepartmentsComboBox() {
    staffDepartmentComboBox.setItems(
        FXCollections.observableArrayList(
            new HashSet<>(commands.listDepartments())
        )
    );
  }

  private String getDepartmentByDepartmentId(long id) {
    return commands.listDepartments().stream()
        .filter((dep) -> dep.getId() == id)
        .collect(Collectors.toSet())
        .iterator().next()
        .getName();
  }

  @FXML
  private void handleAdd() {
    try {
      String name = nameTextField.getText();
      int price = Integer.parseInt(priceTextField.getText());
      int realPrice = Integer.parseInt(realPriceTextField.getText());

      if (isValidPosition(name, price, realPrice)) {
        commands.addPosition(name, price, realPrice);
        nameTextField.setText("");
        priceTextField.setText("");
        realPriceTextField.setText("");
      } else {
        mainApp.showAlert("Invalid data", "Invalid data provided");
      }
    } catch (NumberFormatException e) {
      mainApp.showAlert("Not a number", "Invalid data provided");
    }
    updateMenuTable();
  }

  private boolean isValidPosition(String name, int price, int realPrice) {
    boolean isName = (name.length() > 2);
    boolean isPrice = (price >= 0);
    boolean isRealPrice = (realPrice >= 0);
    return isName && isPrice && isRealPrice;
  }


  @FXML
  private void handleDelete() {
    Position selected = positionsTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    commands.deletePosition(selected.getId());
    updateMenuTable();
  }

  @FXML
  private void handleAddStaff() {
    try {
      String username = staffUsernameField.getText();
      String password = staffPasswordField.getText();
      String firstName = staffFirstNameField.getText();
      String lastName = staffLastNameField.getText();
      int salary = Integer.parseInt(staffSalaryField.getText());
      long departmentId = staffDepartmentComboBox.getValue().getId();
      String role = staffRoleField.getText();

      if (role.equals("ADMIN")) {
        mainApp.showAlert("Really?", "Why u do so?");
        return;
      }

      if (!isValidStaff(username, password, firstName, lastName, salary, role)) {
        mainApp.showAlert("Invalid data", "Invalid data provided");
        return;
      }

      commands.addUser(username, password);

      User user = commands.findUserByUsername(username);
      if (user != null) {
        commands.addStaff(firstName, lastName, salary, departmentId, role, user.getId());
      }

      updateStaffTable();

      staffUsernameField.setText("");
      staffPasswordField.setText("");
      staffFirstNameField.setText("");
      staffLastNameField.setText("");
      staffSalaryField.setText("");
      staffRoleField.setText("");

    } catch (Exception e) {
      mainApp.showAlert("Not a number", "Invalid data provided");
    }
  }


  @FXML
  private void handleDeleteStaff() {
    Staff selected = staffTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;

    if (selected.getRole().equals("ADMIN")) {
      mainApp.showAlert("Please don't", "Nice try, but don't even try it again -_-");
      return;
    }

    commands.deleteUser(selected.getUserId());
    updateStaffTable();
  }

  private boolean isValidStaff(String username, String password, String firstName, String lastName,
                               int salary, String role) {
    boolean isUsername = (username.length() > 2);
    boolean isPassword = (password.length() > 2);
    boolean isFirstName = (firstName.length() > 2);
    boolean isLastName = (lastName.length() > 2);
    boolean isSalary = (salary >= 0 && salary <= 999999999);
    boolean isRole = (role.length() > 2);
    return isUsername && isPassword && isFirstName && isLastName && isSalary && isRole;
  }

  @FXML
  private void handleAddDepartment() {
    String name = departmentNameField.getText();
    if (isValidDepartment(name)) {
      commands.addDepartment(name);
      departmentNameField.setText("");
    } else {
      mainApp.showAlert("Invalid data", "Invalid data provided");
    }
    updateDepartmentsTable();
  }

  @FXML
  private void handleDeleteDepartment() {
    Department selected = departmentsTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    commands.deleteDepartment(selected.getId());
    updateDepartmentsTable();
  }

  private boolean isValidDepartment(String name) {
    return (name.length() > 2);
  }

  @FXML
  private void handleMostPopularPositionButton() {
    Pair<Integer, Position> numberAndPosition = commands.findMostPopularPosition();
    mainApp.showMessage("Most popular position", "The most popular position in menu is ",
        numberAndPosition.getValue().getName() + " : " + numberAndPosition.getKey());
  }

  @FXML
  private void handleMonthlyIncomeButton() {
    Integer income = commands.calculateMonthlyIncome();
    mainApp.showMessage("Monthly income", "Your monthly income was",
        income + " RUB");
  }

  @FXML
  private void logOut() {
    mainApp.setActiveUser(null);
    mainApp.showSignInLayout();
  }

  @FXML
  private void mostPayed() {
    Staff staff = commands.findMostPayedStaff();
    if (staff == null) return;
    mainApp.showMessage("Most payed staff", "Here he is", staff.getFirstName() + " " +
        staff.getLastName() + " : " + staff.getSalary());
  }

  @FXML
  private void lessPayed() {
    Staff staff = commands.findLessPayedStaff();
    if (staff == null) return;
    mainApp.showMessage("Less payed staff", "Here he is", staff.getFirstName() + " " +
        staff.getLastName() + " : " + staff.getSalary());
  }

  @FXML
  private void handleSelect() {
    Position selected = positionsTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    nameTextField.setText(selected.getName());
    priceTextField.setText(selected.getPrice().toString());
    realPriceTextField.setText(selected.getRealPrice().toString());
    addButton.setDisable(true);
    updateButton.setVisible(true);
  }

  @FXML
  private void handleUpdatePosition() {
    Position selected = positionsTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;

    long id = selected.getId();
    String name = nameTextField.getText();
    int price = Integer.parseInt(priceTextField.getText());
    int realPrice = Integer.parseInt(realPriceTextField.getText());
    commands.updatePosition(id, name, price, realPrice);
    nameTextField.setText("");
    priceTextField.setText("");
    realPriceTextField.setText("");
    addButton.setDisable(false);
    updateButton.setVisible(false);
    updateMenuTable();
  }

  @FXML
  private void handleSelectMenuTab() {
    addButton.setDisable(false);
    updateButton.setVisible(false);
  }
}
