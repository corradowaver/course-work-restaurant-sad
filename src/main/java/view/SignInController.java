package view;

import com.sun.tools.javac.Main;
import database.Commands;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import security.PasswordEncoder;

public class SignInController {
  private MainApp mainApp;
  private Commands commands;

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;
    commands = new Commands();
  }

  @FXML
  private void initialize() {
  }

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button adminButton;
  @FXML
  private Button clientButton;
  @FXML
  private Button managerButton;
  @FXML
  private Button signUpButton;

  @FXML
  private void handleSignIn() throws Exception {
    String username = usernameField.getText();
    String password = passwordField.getText();

    User user = commands.findUserByUsername(username);
    if (user != null) {
      String decodedPassword = PasswordEncoder.decode(user.getPassword());
      if (decodedPassword.equals(password)) {
        mainApp.handleSignIn(user.getId());
      } else {
        mainApp.showAlert("Incorrect input", "username or password");
      }
    } else {
      mainApp.showAlert("Incorrect input", "username or password");
    }
  }

  @FXML
  private void handleSignUp() {
    mainApp.handleSignUp();
  }

  @FXML
  private void handleAdminButton() {
    usernameField.setText("admin");
    passwordField.setText("admin");
  }

  @FXML
  private void handleClientButton() {
    usernameField.setText("client");
    passwordField.setText("client");
  }

  @FXML
  private void handleManagerButton() {
    usernameField.setText("manager");
    passwordField.setText("manager");
  }

}
