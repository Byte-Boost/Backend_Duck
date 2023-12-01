package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.byteboost.duck.Main;
import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {
    public static User user;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;
    @FXML
    private PasswordField pf_confirm;
    @FXML
    private Button btn_register;
    @FXML
    private Button btn_back;
    @FXML
    private Label lb_notfilled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_username.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty() && !pf_confirm.getText().trim().isEmpty()) {
                    if (DBUtils.getUserId(tf_username.getText())==null){
                        tf_username.getStyleClass().remove("not-filled");
                        String confirm = pf_confirm.getText();
                        String password = pf_password.getText();

                        if (confirm.equals(password)) {
                            System.out.println("Success! The passwords match.");
                            user = new User(tf_username.getText(),password);
                            Main.redirectedFrom = "signup";
                            GUIUtils.changeScene(event,"/fxml/confirmpage.fxml","Duck - Confirmation");

                        } else {

                            pf_confirm.getStyleClass().add("not-filled");
                            pf_password.getStyleClass().add("not-filled");
                            tf_username.getStyleClass().remove("not-filled");
                            lb_notfilled.setText("Error! Fields don't match.");
                            System.out.println(confirm + "," + password);

                            System.out.println("Error! The passwords don't match.");
                        }
                    } else {
                        pf_confirm.getStyleClass().remove("not-filled");
                        pf_password.getStyleClass().remove("not-filled");
                        tf_username.getStyleClass().add("not-filled");
                        lb_notfilled.setText("Error! This email is already registered");
                    }
                } else {
                    System.out.println("Error! Fields cannot be empty.");
                    lb_notfilled.setText("Error! One or more fields are empty.");
                    tf_username.getStyleClass().add("not-filled");
                    pf_confirm.getStyleClass().add("not-filled");
                    pf_password.getStyleClass().add("not-filled");

                }
            }
        });
        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event, "/fxml/login.fxml","Duck - Login");
            }
        });
    }
}