
package net.byteboost.duck;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application{
    public static String redirectedFrom = "";

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-ExtraBold.ttf"), 16);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/ducklogoround.png"))));

        final boolean resizable = primaryStage.isResizable();

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/fxml/login.fxml")));
        primaryStage.setTitle("Duck - Login");
        primaryStage.setResizable(!resizable);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}