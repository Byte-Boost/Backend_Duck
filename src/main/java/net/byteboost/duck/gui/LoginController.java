package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static User user;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_register;
    @FXML
    private Label error402;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                user = new User(tf_username.getText(),pf_password.getText());
                DBUtils.logInUser(event,user);
                tf_username.getStyleClass().add("not-filled");
                pf_password.getStyleClass().add("not-filled");
                error402.setText("One or more fields are wrong");
            }
        });
        btn_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event, "/fxml/signup.fxml","Duck - Register",null);
            }
        });
    }
}
