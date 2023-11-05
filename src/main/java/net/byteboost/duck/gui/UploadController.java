package net.byteboost.duck.gui;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.FileSystemDocumentLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.byteboost.duck.utils.AIUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UploadController implements Initializable {
    public static Document doc;
    public static File selectedFile;
    public static InputStream stream;
    @FXML
    private Label selectedFileField;
    @FXML
    private Button btn_upload;
    @FXML
    private Button btn_next;
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_registry;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("txt,html,pdf,docx files", "*.txt","*.htm","*.pdf","*.docx")
                );
                Stage currentStage = (Stage) selectedFileField.getScene().getWindow();
                selectedFile = fileChooser.showOpenDialog(currentStage);
                try {
                    stream = new FileInputStream(selectedFile.getPath());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                doc = FileSystemDocumentLoader.loadDocument(AIUtils.formatText(selectedFile.getPath()));

                selectedFileField.setText(selectedFile.getName());

            }
        });
    btn_next.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (selectedFile != null) {
                GUIUtils.changeScene(event,"/fxml/aichat.fxml","Duck - Chat");
            }

        }
    });

    btn_back.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            GUIUtils.changeScene(event,"/fxml/login.fxml","Duck - Login");
        }
    });

    btn_registry.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            GUIUtils.changeScene(event, "/fxml/registry.fxml","Duck - Registry");
        }
    });

    }
}
