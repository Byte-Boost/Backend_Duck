package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static net.byteboost.duck.utils.DBUtils.*;

public class RegistryController implements Initializable {
    @FXML
    private VBox register;
    @FXML
    private Button btn_back;

    private static final User localuser = LoginController.user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        register.setSpacing(10);
        for (int i = 0 ;i < getActivitySize();i++){
            Label registryTitle = new Label("Title: " + getActivityInfo("document_title", "registry_id", i+1));
            Label registryUser = new Label("User: " + getUsername(getActivityInfo("user_id", "registry_id", i+1)));
            Label registryDate = new Label("Date: " + getActivityInfo("access_date","registry_id",i+1));
            HBox hBox = new HBox();
            hBox.getChildren().add(registryDate);
            hBox.getChildren().add(registryUser);
            hBox.getChildren().add(registryTitle);
            hBox.setSpacing(40);
            register.getChildren().add(hBox);
        }

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload");
            }
        });
    }
    int getActivitySize(){
        String sql = "SELECT COUNT(*) FROM activity_register";
        try{

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int rownum = rs.getInt(1);
            rs.close();
            stmt.close();
            connection.close();
            return rownum;

        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }
}
