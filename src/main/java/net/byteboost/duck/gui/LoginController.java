package net.byteboost.duck.gui;

import com.sun.javafx.scene.control.behavior.ToggleButtonBehavior;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private Label shownPassword;
    @FXML
    private ToggleButton btn_password;
    @FXML
    private ImageView login_eye;
    @FXML
    void pf_passwordKeyTyped(KeyEvent event) {
        shownPassword.textProperty().bind(Bindings.concat(pf_password.getText()));
    }
    @FXML
    private Label error402;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        shownPassword.setVisible(false);

        pf_password.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                pf_passwordKeyTyped(event);
            }
        });

        btn_password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (btn_password.isSelected()) {

                    InputStream stream = null;
                    stream = getClass().getResourceAsStream("/images/eye-opened.png");
                    assert stream != null;
                    Image image = new Image(stream);

                    shownPassword.setVisible(true);
                    shownPassword.textProperty().bind(Bindings.concat(pf_password.getText()));
                    btn_password.setText("Hide");
                    pf_password.setStyle("-fx-text-fill: transparent");
                    login_eye.setImage(image);
                }
                else {

                    InputStream stream = null;
                    stream = getClass().getResourceAsStream("/images/eye-closed.png");
                    assert stream != null;
                    Image image = new Image(stream);

                    shownPassword.setVisible(false);
                    btn_password.setText("Show");
                    pf_password.setStyle("-fx-text-fill: black;");
                    login_eye.setImage(image);

                }
            }
        });
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

        };
    }

