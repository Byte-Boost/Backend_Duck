package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.byteboost.duck.models.RegistryEntry;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistryController implements Initializable {
    @FXML
    private VBox register;
    @FXML
    private Button btn_back;
    private HBox addNewRow(String date, String user, String title){
        Label registryDate = new Label("Date: " + date);
        Label registryUser = new Label("User: " + user);
        Label registryTitle = new Label("Title: " + title);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(registryDate, registryUser, registryTitle);
        hBox.setSpacing(40);
        register.getChildren().add(hBox);
        return hBox;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        register.setSpacing(10);
        for (RegistryEntry entry : DBUtils.getUserHistory(LoginController.getUser().getUsername())) {
            addNewRow(entry.getAccess(), LoginController.getUser().getUsername(), entry.getTitle());
        }

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload");
            }
        });

    }
}
