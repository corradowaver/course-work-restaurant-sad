package view;

import database.Commands;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Client;
import model.Order;
import model.Review;
import model.Staff;

import java.util.Map;
import java.util.stream.Collectors;

public class ManagerController {
  private MainApp mainApp;
  private Commands commands = new Commands();

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;
  }

  @FXML
  private TableView<Client> clientTable;
  @FXML
  private TableColumn<Client, String> clientNameColumn;
  @FXML
  private TableColumn<Client, String> clientAddressColumn;
  @FXML
  private TableColumn<Client, String> clientPhoneColumn;

  @FXML
  private TableView<Order> orderTable;
  @FXML
  private TableColumn<Order, String> orderDateColumn;
  @FXML
  private TableColumn<Order, String> orderClientPhoneColumn;

  @FXML
  private TableView<Review> reviewTable;
  @FXML
  private TableColumn<Review, String> reviewOrderIdColumn;
  @FXML
  private TableColumn<Review, String> reviewRateColumn;
  @FXML
  private TableColumn<Review, String> reviewTextColumn;
  @FXML
  private TableColumn<Review, String> reviewClientsNameColumn;
  @FXML
  private TableColumn<Review, String> reviewClientPhoneColumn;
  @FXML
  private TableColumn<Review, String> reviewClientAddressColumn;

  @FXML
  private TextField staffUsernameField;
  @FXML
  private TextField staffFirstNameField;
  @FXML
  private TextField staffLastNameField;
  @FXML
  private TextField staffSalaryField;



  @FXML
  private void initialize() {
    initTables();
    updateClientTable();
    updateOrderTable();
    updateReviewTable();

    orderTable.setRowFactory(tv -> {
      TableRow<Order> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Order rowData = row.getItem();
          var positions = commands.findPositionsByOrderId(rowData.getId());
          Map<String, Integer> nameNumber =
              positions.entrySet().stream()
                  .collect(Collectors.toMap(
                      e -> e.getKey().getName(),
                      Map.Entry::getValue
                      )
                  );
          mainApp.showOrderInfo(rowData.getDate(), nameNumber.toString());
        }
      });
      return row;
    });
  }

  private void initTables() {
    clientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    clientAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
    clientPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));

    orderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
    orderClientPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty( getClientPhoneById(cellData.getValue())));

    reviewOrderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderId().toString()));
    reviewRateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRate().toString()));
    reviewTextColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
    reviewClientsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getClientByReview(cellData.getValue()).getName()));
    reviewClientPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getClientByReview(cellData.getValue()).getPhone()));
    reviewClientAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getClientByReview(cellData.getValue()).getAddress()));

  }

  private void updateClientTable() {
    clientTable.setItems(FXCollections.observableArrayList(commands.listClients()));
  }

  private void updateOrderTable() {
    orderTable.setItems(FXCollections.observableArrayList(commands.listOrders()));
  }

  private void updateReviewTable() {
    reviewTable.setItems(FXCollections.observableArrayList(commands.listReviews()));
  }


  private String getClientPhoneById(Order order) {
    Client client = commands.findClientById(order.getClientId());
    return client != null ? client.getPhone() : "***";
  }

  private Client getClientByReview(Review review) {
    Client client = commands.findClientById(review.getClientId());
    return client != null ? client : new Client("Not found", "Not found", "Not found");
  }

  @FXML
  private void updateProfileInfo() {
    Staff staff = (Staff) mainApp.getActiveUser();
    staffUsernameField.setText(staff.getUsername());
    staffFirstNameField.setText(staff.getFirstName());
    staffLastNameField.setText(staff.getLastName());
    staffSalaryField.setText(staff.getSalary().toString());
  }

  @FXML
  private void logOut() {
    mainApp.setActiveUser(null);
    mainApp.showSignInLayout();
  }
}
