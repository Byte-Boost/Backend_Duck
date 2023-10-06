package net.byteboost.duck.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.byteboost.duck.models.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static net.byteboost.duck.utils.DBUtils.getActivityInfo;
import static net.byteboost.duck.utils.DBUtils.getConnection;

public class RegisterController implements Initializable {
    @FXML
    private VBox register;

    private static final User localuser = LoginController.user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        register.setSpacing(10);
        for (int i = 0 ;i < getActivitySize();i++){
            Label registry = new Label("Titulo do documento: "+ getActivityInfo("document_title","registry_id", i+1) + " | " + " acessado por : " + localuser.getUsername() +  " | " + "Na data: " + getActivityInfo("access_date","registry_id",i+1) );
            HBox hBox = new HBox();
            hBox.getChildren().add(registry);
            register.getChildren().add(hBox);
        }
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
