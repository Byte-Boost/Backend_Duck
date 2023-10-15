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


public class SignUpController implements Initializable {
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

                    String confirm = pf_confirm.getText();
                    String password = pf_password.getText();

                    if (confirm.equals(password)) {
                        System.out.println("Sucesso! As senhas coincidem.");
                        DBUtils.addUser(new User(tf_username.getText(),password));
                        GUIUtils.changeScene(event,"/fxml/login.fxml","Duck - Login",null);

                    } else {

                        pf_confirm.getStyleClass().add("not-filled");
                        pf_password.getStyleClass().add("not-filled");
                        tf_username.getStyleClass().remove("not-filled");
                        lb_notfilled.setText("Error! Fields don't match.");
                        System.out.println(confirm + "," + password);

                        System.out.println("Erro! As senhas não coincidem.");
                    }
                } else {
                    System.out.println("Erro! Os campos se encontram vazios.");
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
                GUIUtils.changeScene(event, "/fxml/login.fxml","Duck - Login",null);
            }
        });
    }
}