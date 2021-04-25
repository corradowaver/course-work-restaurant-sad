import database.Commands;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;
import security.PasswordEncoder;

import java.sql.Date;
import java.util.Map;

public class Main {
  public static void main(String[] args) throws Exception {
    Commands commands = new Commands();
    //commands.addUser(new User("admin", "admin"));
    //commands.addDepartment("Adminskaja");
    //commands.addStaff("SuperDirector", "MegaBoss", 500000, 2004, "ADMIN", 1032);
    //commands.addPosition("Cringeburger", 1000, 50);
    //commands.addOrder(new Date(System.currentTimeMillis()), true, 1005, 1005,
    //    Map.of(1021, 5, 2, 1));
    //commands.addReview(5, "Cringe at all the best", 1005, 1038);
//    System.out.println(commands.listStaff());
//    System.out.println(commands.listMenu());

//    String password = "secretPwd";
//    String encoded = PasswordEncoder.encode(password);
//    String decoded = PasswordEncoder.decode(encoded);
//    System.out.println(encoded);
//    System.out.println(decoded);
  }
}
