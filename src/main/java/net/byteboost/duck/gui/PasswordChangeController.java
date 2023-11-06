package net.byteboost.duck.gui;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PasswordChangeController implements Initializable {
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_back;
    @FXML
    private PasswordField pf_insertPassword;
    @FXML
    private PasswordField pf_confirmPassword;
    @FXML
    private Label lb_ip_showPassword;
    @FXML
    private Label lb_cp_showPassword;
    @FXML
    private Label lb_errorMessage;
    @FXML
    private ToggleButton btn_view;
    @FXML
    private ImageView passwordEye;
    @FXML
    void pf_passwordKeyTyped(KeyEvent event) {
        lb_ip_showPassword.textProperty().bind(Bindings.concat(pf_insertPassword.getText()));
        lb_cp_showPassword.textProperty().bind(Bindings.concat(pf_confirmPassword.getText()));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lb_ip_showPassword.setVisible(false);
        lb_cp_showPassword.setVisible(false);
        lb_errorMessage.setVisible(false);

        pf_insertPassword.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                pf_passwordKeyTyped(event);
            }
        });

        pf_confirmPassword.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                pf_passwordKeyTyped(event);
            }
        });

        btn_view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (btn_view.isSelected()) {

                    InputStream stream;
                    stream = getClass().getResourceAsStream("/images/eye-open.png");
                    assert stream != null;
                    Image image = new Image(stream);

                    lb_ip_showPassword.setVisible(true);
                    lb_cp_showPassword.setVisible(true);
                    lb_ip_showPassword.textProperty().bind(Bindings.concat(pf_insertPassword.getText()));
                    lb_cp_showPassword.textProperty().bind(Bindings.concat(pf_confirmPassword.getText()));
                    pf_insertPassword.setStyle("-fx-text-fill: transparent");
                    pf_confirmPassword.setStyle("-fx-text-fill: transparent");
                    passwordEye.setImage(image);
                }
                else {

                    InputStream stream;
                    stream = getClass().getResourceAsStream("/images/eye-open.png");
                    assert stream != null;
                    Image image = new Image(stream);

                    lb_ip_showPassword.setVisible(false);
                    lb_cp_showPassword.setVisible(false);
                    btn_view.setText("Show");
                    pf_insertPassword.setStyle("-fx-text-fill: black;");
                    pf_confirmPassword.setStyle("-fx-text-fill: black;");
                    passwordEye.setImage(image);

                }
            }
        });

        btn_confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (pf_insertPassword.getText().equals(pf_confirmPassword.getText())) {
                    DBUtils.changeAccountPassword(new User(ForgetPasswordController.email,null), pf_confirmPassword.getText());
                    GUIUtils.changeScene(event, "/fxml/login.fxml", "Duck - Login");
                }

                else {
                    lb_errorMessage.setVisible(true);
                    pf_insertPassword.getStyleClass().add("not-filled");
                    pf_confirmPassword.getStyleClass().add("not-filled");
                }
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event, "/fxml/login.fxml", "Duck - Login");
            }
        });
    };
}

