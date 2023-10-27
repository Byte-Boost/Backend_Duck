package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPasswordController implements Initializable {
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_send;
    @FXML
    private TextField tf_email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event,"/fxml/login.fxml","Duck - Login");
            }
        });

    }
}
