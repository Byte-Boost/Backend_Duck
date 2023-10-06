package net.byteboost.duck.utils;

import dev.langchain4j.data.document.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.byteboost.duck.gui.AiControllerChat;
import net.byteboost.duck.gui.RegisterController;
import net.byteboost.duck.gui.UploadController;
import net.byteboost.duck.models.User;


import java.io.IOException;

public class GUIUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, Document doc){
        Parent root = null;
            try{
                FXMLLoader loader = new FXMLLoader(GUIUtils.class.getResource(fxmlFile));
                root = loader.load();
            }catch (IOException exception){
                exception.printStackTrace();
            }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

    }
}
