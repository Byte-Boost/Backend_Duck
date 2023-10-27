package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ConfirmationPageController implements Initializable {
    @FXML
    private TextField tf_code;
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_send;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Random random = new Random();
        int randomCode = random.nextInt(1000000,9000000);
        System.out.println("Code:"+randomCode);

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event, "/fxml/signup.fxml","Duck - Register");
            }
        });

        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tf_code.getText().equals(String.valueOf(randomCode))) {
                    System.out.println("Sucesso! O Código está correto");
                    DBUtils.addUser(SignUpController.user);
                    GUIUtils.changeScene(event, "/fxml/login.fxml","Duck - Login");
                } else {
                    System.out.println("Erro! Os códigos não coincidem.");
                }
            }
        });
    }
}
