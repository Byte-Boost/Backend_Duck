package net.byteboost.duck.gui;

import dev.langchain4j.data.document.Document;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.byteboost.duck.models.User;
import net.byteboost.duck.utils.AIUtils;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.GUIUtils;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AiControllerChat implements Initializable {
    private static final User localuser = LoginController.user;
    private static final Document doc = UploadController.doc;

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

                System.out.println("\nCHAT INITIATED\n");

                //Adiciona registro da pergunta no banco
                if (!tf_question.getText().trim().isEmpty()) {
                    DBUtils.AddRegistry(localuser.getId(), UploadController.selectedFile.getName(), Date.valueOf(String.valueOf(LocalDate.now()) ));
                    System.out.println("Interaction added to User History" );
                }

                //Criação de labels pergunta no chat
                chat.setSpacing(10);
                Label question = new Label(tf_question.getText());
                question.getStylesheets().add("css/main.css");
                question.getStyleClass().add("question");
                question.setWrapText(true);
                question.setMaxWidth(200);
                HBox hBoxQuestion = new HBox();

                hBoxQuestion.getChildren().add(question);
                hBoxQuestion.setAlignment(Pos.BASELINE_RIGHT);
                hBoxQuestion.setStyle("-fx-border: 12px");
                hBoxQuestion.setStyle("-fx-padding:0 0 0 0");

                chat.getChildren().add(hBoxQuestion);

                System.out.println("Message sent: " + tf_question.getText());

                //Criação de labels resposta do bot no chat
                Label response = new Label(AIUtils.loadIntoHugging(doc, tf_question.getText()));
                HBox hBoxResponse = new HBox();

                response.getStylesheets().add("css/main.css");
                response.getStyleClass().add("response");
                response.setWrapText(true);
                response.setMaxWidth(200);

                hBoxResponse.getChildren().add(response);
                hBoxResponse.setAlignment(Pos.BASELINE_LEFT);
                hBoxResponse.setStyle("-fx-padding:0 0 0 5");

                chat.getChildren().add(hBoxResponse);
                System.out.println("Response received: " + response.getText() + "\n");

            }
        });
        btn_new_file.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload");
            }
        });
    }
}

