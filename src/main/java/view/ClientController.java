package view;

import database.Commands;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import model.Client;
import model.Order;
import model.Position;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class ClientController {
  @FXML
  ImageView doge;
  @FXML
  Button updateHistoryButton;
  private MainApp mainApp;
  private Commands commands = new Commands();
  private List<Position> cart = new ArrayList<>();
  private List<Order> history = new ArrayList<>();
  private Thread dogeThread;
  @FXML
  private TableView<Position> positionsTable;
  @FXML
  private TableColumn<Position, String> positionNameColumn;
  @FXML
  private TableColumn<Position, String> positionPriceColumn;
  @FXML
  private TableView<Position> cartTable;
  @FXML
  private TableColumn<Position, String> cartPositionNameColumn;
  @FXML
  private TableColumn<Position, String> cartPositionPriceColumn;
  @FXML
  private TableColumn<Position, String> cartPositionNumberColumn;
  @FXML
  private TableView<Order> historyTable;
  @FXML
  private TableColumn<Order, String> historyDateColumn;
  @FXML
  private Button addReviewButton;
  @FXML
  private ComboBox<Integer> reviewRateComboBox;
  @FXML
  private TextField reviewTextField;
  @FXML
  private Button addButton;
  @FXML
  private Button editButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField nameField;
  @FXML
  private TextField addressField;
  @FXML
  private TextField phoneField;
  @FXML
  private Tab profileTab;

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;
  }

  @FXML
  private void initialize() {

    initTables();
    updateMenuTable();
    updateCartTable();
    //updateHistoryTable();
    reviewRateComboBox.setItems(
        FXCollections.observableArrayList(List.of(0, 1, 2, 3, 4, 5)));

    historyTable.setRowFactory(tv -> {
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
    positionNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    positionPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));

    cartPositionNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    cartPositionPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
    cartPositionNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(countPositionNumber(cellData.getValue().getName())));

    historyDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
  }

  private String countPositionNumber(String name) {
    return String.valueOf(cart.stream().filter(pos -> pos.getName().equals(name)).count());
  }

  private void updateMenuTable() {
    positionsTable.setItems(FXCollections.observableArrayList(commands.listMenu()));
  }

  private void updateCartTable() {
    cartTable.setItems(FXCollections.observableArrayList(new HashSet<>(cart)));
  }

  private void updateHistoryTable() {
    historyTable.setItems(FXCollections.observableArrayList(commands.listOrdersByClientId(mainApp.getActiveUser().getId())));
  }

  @FXML
  private void handleAdd() {
    Position selected = positionsTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    cart.add(selected);
    updateCartTable();
    cartTable.refresh();
  }

  @FXML
  private void handleBuy() {
    Long clientId = mainApp.getActiveUser().getId();
    Map<Integer, Integer> positionIdNumberMap = new HashMap<>();
    cart.forEach(pos -> {
      positionIdNumberMap.putIfAbsent(
          (int) pos.getId(),
          (int) cart.stream().filter(x -> x.getId() == pos.getId()).count());
    });
    commands.addOrder(new Date(System.currentTimeMillis()), false, clientId, null, positionIdNumberMap);
    cart = List.of();
    updateCartTable();
  }

  @FXML
  private void updateHistory() {
    updateHistoryTable();
  }

  @FXML
  private void updateProfileInfo() {
    mainApp.updateActiveUser();
    Client client = (Client) mainApp.getActiveUser();
    nameField.setText(client.getName());
    addressField.setText(client.getAddress());
    phoneField.setText(client.getPhone());
  }

  @FXML
  private void handleEdit() {
    nameField.setEditable(true);
    addressField.setEditable(true);
    phoneField.setEditable(true);
    nameField.setDisable(false);
    addressField.setDisable(false);
    phoneField.setDisable(false);

    editButton.setDisable(true);
    saveButton.setVisible(true);
  }

  @FXML
  private void handleSave() {
    String name = nameField.getText();
    String address = addressField.getText();
    String phone = phoneField.getText();
    Client newClient = new Client(name, address, phone);
    Client oldClient =  ((Client) mainApp.getActiveUser());

    if (isValidClient(newClient)) {
      commands.updateClient(oldClient.getId(), name, address, phone);

      nameField.setDisable(true);
      addressField.setDisable(true);
      phoneField.setDisable(true);
      editButton.setDisable(false);
      saveButton.setVisible(false);
      updateProfileInfo();
    } else {
      mainApp.showAlert("Invalid data", "Invalid data provided");
    }
  }

  private boolean isValidClient(Client client) {
    boolean isName = (client.getName().length() > 2);
    boolean isAddress = (client.getAddress().length() > 2);
    boolean isPhone = (client.getPhone().length() > 2);
    return isName && isAddress && isPhone;
  }

  @FXML
  private void handleAddReview() {
    Order selected = historyTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      mainApp.showAlert("Select order", "Select order, then fill your review.");
      return;
    }
    int rate = reviewRateComboBox.getValue();
    String text = reviewTextField.getText();
    Client client = (Client) mainApp.getActiveUser();
    commands.addReview(rate, text, client.getId(), selected.getId());
    reviewTextField.setText("");
    mainApp.showMessage("Success", "Successfully added", "Your review was very important to us!");
    updateHistoryTable();
  }

  @FXML
  private void logOut() {
    mainApp.setActiveUser(null);
    mainApp.showSignInLayout();
  }

  private void blurDoge() {
    BoxBlur bb = new BoxBlur();
    bb.setIterations(2);
    int i = 8;
    while (true) {
      while (i < 15) {
        i += 1;
        doJob(bb, i);
      }
      while (i > 1) {
        i -= 1;
        doJob(bb, i);
      }
    }
  }

  private void doJob(BoxBlur bb, int i) {
    bb.setWidth(i);
    bb.setHeight(i);

    int dogeOffset = i - 8;
    doge.setFitWidth(doge.getFitWidth() + dogeOffset);

    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      Thread.currentThread().stop();
    }
    doge.setEffect(bb);
  }

  private void unBlurDoge() {
    dogeThread.interrupt();
    BoxBlur bb = new BoxBlur();
    bb.setWidth(0);
    bb.setHeight(0);
    bb.setIterations(0);
    doge.setEffect(bb);
  }


  @FXML
  private void flexDoge() {
    dogeThread = new Thread(this::blurDoge);
    dogeThread.start();
  }

  @FXML
  private void unFlexDoge() {
    unBlurDoge();
  }
}
