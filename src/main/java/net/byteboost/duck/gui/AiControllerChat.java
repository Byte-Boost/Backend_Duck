package net.byteboost.duck.gui;

import dev.langchain4j.data.document.Document;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.AIUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AiControllerChat implements Initializable {
    private static final User localuser = LoginController.user;
    private static Document doc = UploadController.doc;

    @FXML
    private Button btn_send;
    @FXML
    private TextField tf_question;
    @FXML
    private VBox chat;
    @FXML
    private Button btn_new_file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //Adiciona registro da pergunta no banco
                if (!tf_question.getText().trim().isEmpty()) {
                    DBUtils.AddRegistry(localuser.getId(), UploadController.selectedFile.getName(), Date.valueOf(String.valueOf(LocalDate.now()) ));
                }

                //Criação de labels pergunta no chat
                chat.setSpacing(10);
                Text question = new Text(tf_question.getText());
                question.setWrappingWidth(200);
                HBox hBoxQuestion = new HBox();

                hBoxQuestion.getChildren().add(question);
                hBoxQuestion.setAlignment(Pos.BASELINE_RIGHT);
                hBoxQuestion.setStyle("-fx-border: 12px");
                hBoxQuestion.setStyle("-fx-padding:0 10 0 0");

                chat.getChildren().add(hBoxQuestion);

                //Criação de labels resposta do bot no chat
                Text response = new Text(AIUtils.loadIntoHugging(doc, tf_question.getText()));
                HBox hBoxResponse = new HBox();
                response.setWrappingWidth(200);
                hBoxResponse.getChildren().add(response);
                hBoxResponse.setAlignment(Pos.BASELINE_LEFT);
                hBoxResponse.setStyle("-fx-padding:0 30 0 0");

                chat.getChildren().add(hBoxResponse);

            }
        });
        btn_new_file.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload",null);
            }
        });
    }
}

