package view;

import database.Commands;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User;


public class SignUpController {
  private MainApp mainApp;
  private Commands commands = new Commands();

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;
  }

  @FXML
  private Button signInButton;
  @FXML
  private Button submitButton;

  @FXML
  private TextField usernameField;
  @FXML
  private TextField passwordField;
  @FXML
  private TextField nameField;
  @FXML
  private TextField addressField;
  @FXML
  private TextField phoneField;


  @FXML
  private void initialize() {
  }

  @FXML
  private void handleSignIn() {
    mainApp.showSignInLayout();
  }

  @FXML
  private void handleSubmit() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    String name = nameField.getText();
    String address = addressField.getText();
    String phone = phoneField.getText();

    if (isValidClient(username, password, name, address, phone)) {
      commands.addUser(username, password);
      User user = commands.findUserByUsername(username);
      if (user != null) {
        commands.addClient(name, address, phone, user.getId());
        mainApp.showSignInLayout();
        mainApp.showAlert("Success", "Successfully added");
      } else {
        mainApp.showAlert("Error", "User already exists");
      }
    } else {
      mainApp.showAlert("Invalid data", "Invalid data provided");
    }
  }

  private boolean isValidClient(String username, String password, String name, String address, String phone) {
    return true;
  }


}
