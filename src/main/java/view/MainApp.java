package view;

import database.Commands;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Client;
import model.Staff;
import model.User;

import java.io.IOException;
import java.sql.Date;

public class MainApp extends Application {
  private Stage primaryStage;
  private BorderPane rootLayout;
  private Commands commands;
  private User activeUser;
  private long userId;

  public MainApp() {
    try {
      this.commands = new Commands();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Application.launch(MainApp.class, args);
  }

  public void handleSignIn(long userId) {
    this.userId = userId;
    User user = commands.findClientByUserId(userId);
    if (user == null) {
      user = commands.findStaffByUserId(userId);
      activeUser = user;
      if (user != null) {
        if (((Staff) user).getRole().equals("ADMIN")) {
          showAdminLayout();
        } else {
          showManagerLayout();
        }
      }
    } else {
      activeUser = user;
      showClientLayout();
    }
  }

  public void handleSignUp() {
    showSignUpLayout();
  }

  public void initRootLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
      rootLayout = loader.load();
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
      RootController controller = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showSignInLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("/view/SignInLayout.fxml"));
      AnchorPane loginPage = loader.load();
      rootLayout.setCenter(loginPage);
      SignInController controller = loader.getController();
      controller.setMainApp(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showClientLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("/view/ClientLayout.fxml"));
      AnchorPane clientPage = loader.load();
      rootLayout.setCenter(clientPage);
      ClientController controller = loader.getController();
      controller.setMainApp(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showAdminLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("/view/AdminLayout.fxml"));
      AnchorPane adminPage = loader.load();
      rootLayout.setCenter(adminPage);
      AdminController controller = loader.getController();
      controller.setMainApp(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showManagerLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("/view/ManagerLayout.fxml"));
      AnchorPane manager = loader.load();
      rootLayout.setCenter(manager);
      ManagerController controller = loader.getController();
      controller.setMainApp(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showSignUpLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("/view/SignUpLayout.fxml"));
      AnchorPane registrationPage = loader.load();
      rootLayout.setCenter(registrationPage);
      SignUpController controller = loader.getController();
      controller.setMainApp(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Cringe Restaurant");
    primaryStage.setResizable(false);
    initRootLayout();
    showSignInLayout();
  }

  public void showAlert(String header, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.initOwner(primaryStage);
    alert.setTitle("Error occurred");
    alert.setHeaderText(header);
    alert.setContentText(message);

    alert.showAndWait();
  }

  public void showOrderInfo(Date date, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.initOwner(primaryStage);
    alert.setTitle("Order information");
    alert.setHeaderText("Order " + date.toString());
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void showMessage(String title, String header, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.initOwner(primaryStage);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public void updateActiveUser() {
    Client client = commands.findClientByUserId(userId);
    if (client != null) {
      activeUser = client;
    } else {
      activeUser = commands.findStaffByUserId(userId);
    }
  }
}
