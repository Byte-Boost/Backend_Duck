package net.byteboost.duck.gui;

import dev.langchain4j.data.document.Document;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.byteboost.duck.utils.AIutils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.byteboost.duck.utils.DButils;

import java.sql.*;
import java.time.LocalDate;
import java.net.URL;
import java.util.ResourceBundle;

import static net.byteboost.duck.utils.DButils.getConnection;


public class AiControllerChat implements Initializable {
    private static String user;
    private static Document doc;
    public static void saveUserInformation(String username, Document document){
        user = username;
        doc = document;
    }
    @FXML
    private Button btn_send;
    @FXML
    private TextField tf_question;
    @FXML
    private VBox chat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //Adiciona registro da pergunta no banco
                if (!tf_question.getText().trim().isEmpty()){
                    DButils.AddRegistry(userID(user),UploadController.selectedFile.getName(),Time.valueOf(String.valueOf(LocalDate.now())));
                }

                //Criação de labels pergunta no chat
                chat.setSpacing(10);
                Label question = new Label(tf_question.getText());
                HBox hBoxQuestion = new HBox();

                hBoxQuestion.getChildren().add(question);
                hBoxQuestion.setAlignment(Pos.BASELINE_RIGHT);
                hBoxQuestion.setStyle("-fx-padding:0 30 0 0");

                chat.getChildren().add(hBoxQuestion);

                //Criação de labels resposta do bot no chat
                Label response = new Label(AIutils.loadIntoHugging(doc, tf_question.getText()));
                HBox hBoxResponse = new HBox();

                hBoxResponse.getChildren().add(response);
                hBoxResponse.setAlignment(Pos.BASELINE_LEFT);
                hBoxResponse.setStyle("-fx-padding:0 30 0 0");

                chat.getChildren().add(hBoxResponse);

            }
        });
    }
    int userID(String user){
        String sql = "SELECT user_id FROM users WHERE username=?";
        try {
            Connection cn = getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs;

            stmt.setString(1,user);

            rs = stmt.executeQuery();

            return rs.getInt(1);
            
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
